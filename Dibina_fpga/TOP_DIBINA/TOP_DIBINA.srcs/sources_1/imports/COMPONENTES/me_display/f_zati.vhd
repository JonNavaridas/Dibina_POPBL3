----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 12.11.2020 14:38:08
-- Design Name: 
-- Module Name: f_zati - Behavioral
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
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity f_zati is
    Port ( clk : in STD_LOGIC;
           clk_1s: out std_logic;
           rst : in STD_LOGIC);
end f_zati;

architecture Behavioral of f_zati is

signal kontador: std_logic_vector(16 downto 0);--21
begin

--"010111110101111000010000000"

    process (clk,rst)
        begin
            if rst='1' then kontador<=(others=>'0');
            elsif (clk'event and clk='1') then
            if kontador="11000011010100000" then kontador<=(others=>'0');-- 11000011010100000-> 100000 eta simulazioan=1010 ->10
            else  kontador<=kontador + 1;
            end if;
            if kontador<"01100001101010000" then-- 1100001101010000-> 50000 eta simulazioan=0101 ->5
                    clk_1s<='1';
                else
                    clk_1s<='0';
            end if;
        end if;
    end process;
    

end Behavioral;
