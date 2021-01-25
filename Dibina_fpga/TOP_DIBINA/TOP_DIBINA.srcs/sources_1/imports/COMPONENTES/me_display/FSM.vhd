----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 25.11.2020 13:53:43
-- Design Name: 
-- Module Name: FSM - Behavioral
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

entity FSM is
    Port ( clk : in STD_LOGIC;
           rst : in STD_LOGIC;
           kat0: in STD_LOGIC_VECTOR(6 downto 0);
           kat1: in STD_LOGIC_VECTOR(6 downto 0);
           kat2: in STD_LOGIC_VECTOR(6 downto 0);
           kat3: in STD_LOGIC_VECTOR(6 downto 0);
           punto: out STD_LOGIC;
           anodo : out STD_LOGIC_VECTOR (3 downto 0);
           seg : out STD_LOGIC_VECTOR (6 downto 0));
end FSM;

architecture Behavioral of FSM is

type ESTADO is (ZEN0, ZEN1,ZEN2,ZEN3,GELDI);
signal ACTUAL, SIG: ESTADO;
begin
seq: process(clk,rst)
begin
    if rst='1' then
    ACTUAL<=GELDI;
    elsif clk'event and clk='1' then
     ACTUAL<=SIG;
     end if;
     
     end process;

comb: process (ACTUAL,clk)--En cada estado enseña un numero y va rotando para hacer el efecto visual de que estan los cuatro encendidios
begin     
  case ACTUAL is 
  when GELDI=>
  seg<="0000000";
  anodo<="1111";
  punto<='1';
  if rst='0' then
  SIG<=ZEN0;
  end if;
  when ZEN0=>
  seg<=kat0;
  anodo<="0111";
  punto<='0';
  SIG<=ZEN1;
  when ZEN1=>
  seg<=kat1;
  anodo<="1011";
  punto<='1';
  SIG<=ZEN2;
  when ZEN2=>
  seg<=kat2;
  anodo<="1101";
  punto<='1';
  SIG<=ZEN3;
  when ZEN3=>
  seg<=kat3;
  anodo<="1110";
  punto<='1';
  SIG<=ZEN0;
  when others =>
  seg<="0000000";
  anodo<="0000";
  punto<='1';
  
end case;
end process;


end Behavioral;
