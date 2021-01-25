----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 25.11.2020 13:53:43
-- Design Name: 
-- Module Name: top - Behavioral
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

entity ME_display is
    Port ( clk : in STD_LOGIC;
           rst : in STD_LOGIC;
           kat0: in STD_LOGIC_VECTOR(6 downto 0);
           kat1: in STD_LOGIC_VECTOR(6 downto 0);
           kat2: in STD_LOGIC_VECTOR(6 downto 0);
           kat3: in STD_LOGIC_VECTOR(6 downto 0);
           punto: out STD_LOGIC;
           seg : out STD_LOGIC_VECTOR (6 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0));
end ME_display;

architecture Behavioral of ME_display is
component FSM 
    Port ( clk : in STD_LOGIC;
           rst : in STD_LOGIC;
           kat0: in STD_LOGIC_VECTOR(6 downto 0);
           kat1: in STD_LOGIC_VECTOR(6 downto 0);
           kat2: in STD_LOGIC_VECTOR(6 downto 0);
           kat3: in STD_LOGIC_VECTOR(6 downto 0);
           punto: out STD_LOGIC;
           seg : out STD_LOGIC_VECTOR (6 downto 0);
           anodo : out STD_LOGIC_VECTOR (3 downto 0));
end component;
component f_zati 
    Port ( clk : in STD_LOGIC;
           clk_1s: out std_logic;
           rst : in STD_LOGIC);
end component;
signal clk_f:std_logic;
begin


egoera: FSM port map(
           clk =>clk_f,
           rst =>rst,
           kat0=>kat0,
           kat1=>kat1,
           kat2=>kat2,
           kat3=>kat3,
           punto=>punto,
           seg=>seg,
           anodo =>an);
swaper: f_zati port map (
           clk =>clk,
           clk_1s=>clk_f,
           rst=>rst);

end Behavioral;