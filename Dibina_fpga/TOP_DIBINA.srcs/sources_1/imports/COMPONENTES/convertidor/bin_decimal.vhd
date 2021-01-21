----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.12.2020 11:36:32
-- Design Name: 
-- Module Name: bin_decimal - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.numeric_std.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity bin_decimal is
    Port ( clk : in STD_LOGIC;
           bin : in STD_LOGIC_VECTOR (11 downto 0);
           dec_0 : out STD_LOGIC_VECTOR (3 downto 0);
           dec_1 : out STD_LOGIC_VECTOR (3 downto 0);
           dec_2 : out STD_LOGIC_VECTOR (3 downto 0));
end bin_decimal;

architecture Behavioral of bin_decimal is
type t_BCD_State is (s_IDLE, s_SHIFT, s_CHECK_SHIFT_INDEX, s_ADD,
                       s_CHECK_DIGIT_INDEX, s_BCD_DONE);
  signal r_SM_Main : t_BCD_State := s_IDLE;
 
  -- The vector that contains the output BCD
  signal r_BCD : std_logic_vector(11 downto 0) := (others => '0');
 
  -- The vector that contains the input binary value being shifted.
  signal r_Binary : std_logic_vector(11 downto 0) := (others => '0');
   
  -- Keeps track of which Decimal Digit we are indexing
  signal r_Digit_Index : natural range 0 to 2 := 0;
 
  -- Keeps track of which loop iteration we are on.
  -- Number of loops performed = g_INPUT_WIDTH
  signal r_Loop_Count : natural range 0 to 11  := 0;
begin

 Double_Dabble : process (clk)
    variable v_Upper     : natural;
    variable v_Lower     : natural;
    variable v_BCD_Digit : unsigned(3 downto 0);
  begin
    if clk'event and clk='1' then
 
      case r_SM_Main is
 
        -- Stay in this state until i_Start comes along
        when s_IDLE =>
          
            r_BCD     <= (others => '0');
            if bin> "001111100111" then
                r_Binary<="001111100111";
            else 
                r_Binary<=bin;
            end if;
            r_SM_Main <= s_SHIFT;
          
 
 
        -- Always shift the BCD Vector until we have shifted all bits through
        -- Shift the most significant bit of r_Binary into r_BCD lowest bit.
        when s_SHIFT =>
          r_BCD     <= r_BCD(r_BCD'left-1 downto 0) & r_Binary(r_Binary'left);
          r_Binary  <= r_Binary(r_Binary'left-1 downto 0) & '0';
          r_SM_Main <= s_CHECK_SHIFT_INDEX;
 
 
        -- Check if we are done with shifting in r_Binary vector
        when s_CHECK_SHIFT_INDEX => 
          if r_Loop_Count = 11 then
            r_Loop_Count <= 0;
            r_SM_Main    <= s_BCD_DONE;
          else
            r_Loop_Count <= r_Loop_Count + 1;
            r_SM_Main    <= s_ADD;
          end if;
 
 
        -- Break down each BCD Digit individually.  Check them one-by-one to
        -- see if they are greater than 4.  If they are, increment by 3.
        -- Put the result back into r_BCD Vector.  Note that v_BCD_Digit is
        -- unsigned.  Numeric_std does not perform math on std_logic_vector.
        when s_ADD =>
          v_Upper     := r_Digit_Index*4 + 3;
          v_Lower     := r_Digit_Index*4;
          case V_Lower is
          when 0=>
          v_BCD_Digit := unsigned(r_BCD(3 downto 0));
          when 4=>
          v_BCD_Digit := unsigned(r_BCD(7 downto 4));
          when 8=>
          v_BCD_Digit := unsigned(r_BCD(11 downto 8));
          when others =>
          null;
          end case;
          
           
          if v_BCD_Digit > 4 then
            v_BCD_Digit := v_BCD_Digit + 3;
          end if;
          
          case V_Lower is
          when 0=>
          r_BCD(3 downto 0) <= std_logic_vector(v_BCD_Digit);
          when 4=>
          r_BCD(7 downto 4) <= std_logic_vector(v_BCD_Digit);
          when 8=>
          r_BCD(11 downto 8) <= std_logic_vector(v_BCD_Digit);
          when others =>
          null;
          end case;
          --r_BCD(v_Upper downto v_Lower) <= std_logic_vector(v_BCD_Digit);
          r_SM_Main <= s_CHECK_DIGIT_INDEX;
 
 
        -- Check if we are done incrementing all of the BCD Digits
        when s_CHECK_DIGIT_INDEX =>
          if r_Digit_Index = 2 then
            r_Digit_Index <= 0;
            r_SM_Main     <= s_SHIFT;
          else
            r_Digit_Index <= r_Digit_Index + 1;
            r_SM_Main     <= s_ADD;
          end if;
 
 
        when s_BCD_DONE =>
          dec_0 <= r_BCD(3 downto 0);
          dec_1 <= r_BCD(7 downto 4);
          dec_2 <= r_BCD(11 downto 8);
          r_SM_Main <= s_IDLE;
 
           
        when others =>
          dec_0 <= "0000";
          dec_1 <= "0000";
          dec_2 <= "0000";
          r_SM_Main <= s_IDLE;
           
      end case;
    end if;                             -- rising_edge(i_Clock)
  end process Double_Dabble;

end Behavioral;