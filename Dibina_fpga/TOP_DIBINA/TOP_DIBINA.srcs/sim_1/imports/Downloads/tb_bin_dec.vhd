----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 18.01.2021 17:44:33
-- Design Name: 
-- Module Name: tb_bin_dec - Behavioral
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

entity tb_bin_dec is
--  Port ( );
end tb_bin_dec;

architecture Behavioral of tb_bin_dec is

component bin_decimal is
    Port ( clk : in STD_LOGIC;
           bin : in STD_LOGIC_VECTOR (11 downto 0);
           dec_0 : out STD_LOGIC_VECTOR (3 downto 0);
           dec_1 : out STD_LOGIC_VECTOR (3 downto 0);
           dec_2 : out STD_LOGIC_VECTOR (3 downto 0));
end component;

signal clk:  STD_LOGIC;
signal bin:  STD_LOGIC_VECTOR (11 downto 0);
signal dec_0, dec_1, dec_2:  STD_LOGIC_VECTOR (3 downto 0);

begin
dut: bin_decimal port map (clk=>clk,bin=>bin,dec_0=>dec_0,
                            dec_1=>dec_1,dec_2=>dec_2);
                            
erlojua: process 
begin
clk<='0';
wait for 1ns;
clk<='1';
wait for 1ns;
end process;

stim_proc: process
begin
wait for 0ns; 
bin<="000010101001";
wait for 183ns; --El proceso de shiftear, tarda 183ns en calcular la separacion de numeros 
bin<="000000100011";
wait for 183ns;
bin<="001001000011"; 
wait for 183ns;
bin<="000101011010";
wait for 183ns;
bin<="000000011010";
wait;
end process;


end Behavioral;
