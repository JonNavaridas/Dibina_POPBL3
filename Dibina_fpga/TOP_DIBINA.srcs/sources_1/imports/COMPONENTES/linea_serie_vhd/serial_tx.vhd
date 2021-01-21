----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 22.12.2020 19:09:36
-- Design Name: 
-- Module Name: serial_tx - Behavioral
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

entity serial_tx is
Port (        data_in : in std_logic_vector(7 downto 0);
                bidali : in std_logic;
               serial_out : out std_logic;
              completado : out std_logic;
                      clk : in std_logic);
end serial_tx;

architecture Behavioral of serial_tx is
component kcuart_tx is
    Port (        data_in : in std_logic_vector(7 downto 0);
           send_character : in std_logic;
             en_16_x_baud : in std_logic;
               serial_out : out std_logic;
              Tx_complete : out std_logic;
                      clk : in std_logic);
end component;
component baud is
 Port ( clk : in STD_LOGIC;
        en_16_x_baud: out std_logic);
end component;
signal baud_seinalea: std_logic;
begin

transmisor: kcuart_tx port map(
                  data_in =>data_in,
           send_character =>bidali,
             en_16_x_baud =>baud_seinalea,
               serial_out =>serial_out,
              Tx_complete =>completado,
                      clk =>clk);
baud_comp: baud port map(
              clk =>clk,
              en_16_x_baud=>baud_seinalea);

end Behavioral;
