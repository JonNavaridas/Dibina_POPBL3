----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 19.01.2021 13:57:36
-- Design Name: 
-- Module Name: tb_ME_display - Behavioral
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

entity tb_ME_display is
--  Port ( );
end tb_ME_display;

architecture Behavioral of tb_ME_display is

component ME_display
    Port ( clk : in STD_LOGIC;
           rst : in STD_LOGIC;
           kat1: in STD_LOGIC_VECTOR(6 downto 0);
           kat2: in STD_LOGIC_VECTOR(6 downto 0);
           kat3: in STD_LOGIC_VECTOR(6 downto 0);
           kat4: in STD_LOGIC_VECTOR(6 downto 0);
           punto: out STD_LOGIC;
           seg : out STD_LOGIC_VECTOR (6 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0));
end component;

signal clk, rst, punto:  STD_LOGIC;
signal an:  STD_LOGIC_VECTOR (3 downto 0);
signal kat1, kat2, kat3, kat4, seg:  STD_LOGIC_VECTOR (6 downto 0);

begin

dut: ME_display port map (clk=>clk,rst=>rst,kat1=>kat1, kat2=>kat2,
              kat3=>kat3,kat4=>kat4, punto=>punto, seg=>seg, an=>an);
                            
erlojua: process 
begin
clk<='0';
wait for 1ns;
clk<='1';
wait for 1ns;
end process;

stim_proc: process
begin
wait for 4ns;
rst<='1';
wait for 4ns;
rst<='0';
--wait for 4ns;
kat1<="0001001"; --H
kat2<="1000000"; --O
kat3<="1000111"; --L
kat4<="0001000"; --A
wait for 88ns;
kat1<="1000110"; --C
kat2<="1001100"; --O
kat3<="1001000"; --M
kat4<="1000000"; --O
wait for 88ns;
kat1<="1000110"; --E
kat2<="1001100"; --S
kat3<="1001000"; --T
kat4<="1000000"; --A
wait;
end process;

end Behavioral;
