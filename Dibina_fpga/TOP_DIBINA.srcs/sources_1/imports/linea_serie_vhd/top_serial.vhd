----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 29.12.2020 19:19:25
-- Design Name: 
-- Module Name: top_serial - Behavioral
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

entity top_serial is
    Port ( clk : in STD_LOGIC;
           completado: out std_logic;
           bidali : in std_logic;
           data_in : in std_logic_vector(7 downto 0);
           serial_out : out STD_LOGIC;
           recibido: out std_logic;
           data_out : out std_logic_vector(7 downto 0);
           serial_in: in std_logic);
end top_serial;

architecture Behavioral of top_serial is
component serial_tx is
Port (        data_in : in std_logic_vector(7 downto 0);
                bidali : in std_logic;
               serial_out : out std_logic;
              completado : out std_logic;
                      clk : in std_logic);
end component;
component serial_rx is
Port (        data_out : out std_logic_vector(7 downto 0);
               serial_in : in std_logic;
              recibido : out std_logic;
                      clk : in std_logic);
end component;
begin
transmisor: serial_tx port map(
                 data_in=>data_in,
                bidali =>bidali,
               serial_out=> serial_out,
              completado =>completado,
                      clk =>clk);
receptor: serial_rx port map(
                  data_out =>data_out,
                     serial_in=>serial_in,
                    recibido =>recibido,
                      clk =>clk);                      

end Behavioral;
