----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 22.12.2020 20:11:27
-- Design Name: 
-- Module Name: serial_rx - Behavioral
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

entity serial_rx is
Port (        data_out : out std_logic_vector(7 downto 0);
               serial_in : in std_logic;
              recibido : out std_logic;
                      clk : in std_logic);
end serial_rx;

architecture Behavioral of serial_rx is
component kcuart_rx is
    Port (      serial_in : in std_logic;  
                 data_out : out std_logic_vector(7 downto 0);
              data_strobe : out std_logic;
             en_16_x_baud : in std_logic;
                      clk : in std_logic);
end component;
component baud is
 Port ( clk : in STD_LOGIC;
        en_16_x_baud: out std_logic);
end component;
signal baud_seinalea: std_logic;
begin
receptor: kcuart_rx port map(
                  data_out =>data_out,
             en_16_x_baud =>baud_seinalea,
               serial_in =>serial_in,
               data_strobe=>recibido,
                      clk =>clk);
baud_comp: baud port map(
              clk =>clk,
              en_16_x_baud=>baud_seinalea);
end Behavioral;
