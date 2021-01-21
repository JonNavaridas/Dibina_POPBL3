----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 29.12.2020 12:03:32
-- Design Name: 
-- Module Name: mux - Behavioral
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

entity mux is
    Port ( SEL: in STD_LOGIC_VECTOR(2 DOWNTO 0);
   producto : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   cantidad : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   empresa : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   bien : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   mal : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   vacio : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   almacen : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   mas : in STD_LOGIC_VECTOR(6 DOWNTO 0);
   IRT: out STD_LOGIC_VECTOR(6 DOWNTO 0));
end mux;

architecture Behavioral of mux is

begin

WITH SEL SELECT

IRT<= producto when "000",
      cantidad when "001",
      empresa when "010",
      bien when "011", --bien
      mal when "100", --Mal
      vacio when "101", --vacio
      almacen when "110", 
      mas when "111", --Mas
      "0111111" when others;
      
end Behavioral;
