----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 29.12.2020 11:34:38
-- Design Name: 
-- Module Name: abecedario - Behavioral
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

entity abecedario is
    Port ( sel : in STD_LOGIC_VECTOR (4 downto 0);
       letra : out STD_LOGIC_VECTOR (6 downto 0));
end abecedario;

architecture Behavioral of abecedario is

begin

with sel select
letra<="0001000" when "00000", --A 0
     "0000011" when "00001", --b1
     "1000110" when "00010", --C2
     "0100001" when "00011", --d3
     "0000110" when "00100", --E4
     "0001110" when "00101", --F5
     "0010000" when "00110", --g6
     "0001001" when "00111", --H7
     "1111001" when "01000", --I8
     "1100001" when "01001", --J9
     "0111111" when "01010", --K (-)10
     "1000111" when "01011", --L 11
     "1001000" when "01100", --M 12
     "0101011" when "01101", --n13
     "0101010" when "01110", --ñ14
     "1000000" when "01111", --0  15 
     "0001100" when "10000", --P  16
     "0111111" when "10001", --Q (-)17                             
     "0101111" when "10010", --r 18 
     "0010010" when "10011", --s 19 
     "0001111" when "10100", --t 20
     "1000001" when "10101", --U 21
     "1100011" when "10110", --v 22
     "0111111" when "10111", --W (-)23
     "0111111" when "11000", --X (-)24
     "0010001" when "11001", --y 25  
     "0100100" when "11010", --Z 26          
     "0111111" when others;

end Behavioral;
