----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 19.01.2021 14:12:15
-- Design Name: 
-- Module Name: tb_top_convertidor - Behavioral
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

entity tb_top_convertidor is
--  Port ( );
end tb_top_convertidor;

architecture Behavioral of tb_top_convertidor is

component top_convertidor
    Port ( clk : in STD_LOGIC;
           almacen : in STD_LOGIC_VECTOR (3 downto 0);
           empresa : in STD_LOGIC_VECTOR (4 downto 0);
           opcion : in STD_LOGIC_VECTOR (2 downto 0);
           cantidad : in STD_LOGIC_VECTOR (9 downto 0);
           producto : in STD_LOGIC_VECTOR (4 downto 0);
           kat0, kat1, kat2, kat3: out STD_LOGIC_VECTOR(6 downto 0));
end component;

signal clk:  STD_LOGIC;
signal opcion:  STD_LOGIC_VECTOR (2 downto 0);
signal almacen:  STD_LOGIC_VECTOR (3 downto 0);
signal empresa, producto:  STD_LOGIC_VECTOR (4 downto 0);
signal cantidad:  STD_LOGIC_VECTOR (9 downto 0);
signal kat0, kat1, kat2, kat3:  STD_LOGIC_VECTOR (6 downto 0);

begin

dut: top_convertidor port map (clk=>clk,opcion=>opcion,almacen=>almacen, empresa=>empresa,
              producto=>producto,cantidad=>cantidad, kat0=>kat0, kat1=>kat1, kat2=>kat2, kat3=>kat3);
                            
erlojua: process 
begin
clk<='0';
wait for 1ns;
clk<='1';
wait for 1ns;
end process;

stim_proc: process
begin
wait for 20ns;
opcion<="000"; --producto
almacen<="0010"; 
producto<="10011"; 
cantidad<="0111011100"; 
empresa<="00011"; 
wait for 20ns;
opcion<="001"; --cantidad
almacen<="1010"; 
producto<="10010"; 
cantidad<="0110011000"; 
empresa<="00001";  
wait for 20ns;
opcion<="010"; --empresa
almacen<="0011"; 
producto<="10001"; 
cantidad<="1101011111"; 
empresa<="00000"; 
wait for 20ns;
opcion<="011"; --bien
almacen<="1010"; 
producto<="11100"; 
cantidad<="0001000111"; 
empresa<="11011";  
wait for 20ns;
opcion<="100"; --mal
almacen<="0110"; 
producto<="01011"; 
cantidad<="1000000001"; 
empresa<="01010";    
wait for 20ns;
opcion<="100"; -- vacio (guiones)
almacen<="0000"; 
producto<="00111"; 
cantidad<="0101001000"; 
empresa<="10001";       
wait for 20ns;
opcion<="110"; --almacen
almacen<="0100"; 
producto<="11011"; 
cantidad<="1101001111"; 
empresa<="11110"; 
wait for 20ns;
opcion<="111"; --mas
almacen<="0000"; 
producto<="00111"; 
cantidad<="1000000100"; 
empresa<="01010";    
wait;
end process;

end Behavioral;
