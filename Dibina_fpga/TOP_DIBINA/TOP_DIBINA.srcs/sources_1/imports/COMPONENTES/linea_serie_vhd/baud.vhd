----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 15.12.2020 18:06:15
-- Design Name: 
-- Module Name: baud - Behavioral
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

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity baud is
 Port ( clk : in STD_LOGIC;
        en_16_x_baud: out std_logic);
end baud;

architecture Behavioral of baud is
signal baud_count: integer range 0 to 54 :=0;--650 serial 54 bluetooth
begin
--(100000000/(16*x))=bytes por segundo
baud_timer: process(clk)
begin
    if clk'event and clk='1' then
     if baud_count>=54 then
      baud_count<=0;
      en_16_x_baud<='1';
     else
      baud_count<=baud_count+1;
      en_16_x_baud<='0';
     end if;
  end if;
end process;
end Behavioral;
