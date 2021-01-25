----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 29.12.2020 13:18:53
-- Design Name: 
-- Module Name: TOP_DIBINA - Behavioral
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

entity TOP_DIBINA is
    Port ( rst : in STD_LOGIC;
           clk : in STD_LOGIC;
           sw: in STD_LOGIC_VECTOR(9 downto 0);
           recibir : in STD_LOGIC; --w19
           sacar : in STD_LOGIC; --t17
           confirmar : in STD_LOGIC; --t18
           denegar : in STD_LOGIC; --u17
           serial_in: in STD_LOGIC;
           punto: out STD_LOGIC;
           katodo: out STD_LOGIC_VECTOR(6 downto 0);
           anodo: out STD_LOGIC_VECTOR(3 downto 0);
           cts: out STD_LOGIC;
           rts: out STD_LOGIC;
           JA: out STD_LOGIC_VECTOR(3 downto 0);
           serial_out: out STD_LOGIC);
end TOP_DIBINA;

architecture Behavioral of TOP_DIBINA is

component TOP_Principal is
    Port ( rst : in STD_LOGIC;
           clk : in STD_LOGIC;
           sw : in STD_LOGIC_VECTOR (9 downto 0);
           recibir : in STD_LOGIC; --w19
           sacar : in STD_LOGIC; --t17
           confirmar : in STD_LOGIC; --t18
           denegar : in STD_LOGIC; --u17
           datos_correctos: in STD_LOGIC_VECTOR(7 DOWNTO 0);
           envio_completado: in STD_LOGIC; 
           recibido : in STD_LOGIC; 
           bidali: out STD_LOGIC;
           almacen: out STD_LOGIC_VECTOR (3 downto 0);
           cantidad : out STD_LOGIC_VECTOR (9 downto 0);
           empresa : out STD_LOGIC_VECTOR (4 downto 0);
           producto : out STD_LOGIC_VECTOR (4 downto 0);
           sel : out STD_LOGIC_VECTOR (2 downto 0);
           data_out: out STD_LOGIC_VECTOR (7 downto 0));
end component;

component top_convertidor is
    Port ( clk : in STD_LOGIC;
           almacen : in STD_LOGIC_VECTOR (3 downto 0);
           empresa : in STD_LOGIC_VECTOR (4 downto 0);
           opcion : in STD_LOGIC_VECTOR (2 downto 0);
           cantidad : in STD_LOGIC_VECTOR (9 downto 0);
           producto : in STD_LOGIC_VECTOR (4 downto 0);
           kat0, kat1, kat2, kat3: out STD_LOGIC_VECTOR(6 downto 0));
end component;

component ME_display is
    Port ( clk : in STD_LOGIC;
           rst : in STD_LOGIC;
           kat0: in STD_LOGIC_VECTOR(6 downto 0);
           kat1: in STD_LOGIC_VECTOR(6 downto 0);
           kat2: in STD_LOGIC_VECTOR(6 downto 0);
           kat3: in STD_LOGIC_VECTOR(6 downto 0);
           punto: out STD_LOGIC;
           seg : out STD_LOGIC_VECTOR (6 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0));
end component;

component top_serial is
    Port ( clk : in STD_LOGIC;
           completado: out std_logic;
           bidali : in std_logic;
           data_in : in std_logic_vector(7 downto 0);
           serial_out : out STD_LOGIC;
           recibido: out std_logic;
           data_out : out std_logic_vector(7 downto 0);
           serial_in: in std_logic);
end component;

signal s_cantidad: STD_LOGIC_VECTOR (9 downto 0);
signal s_empresa, s_producto: STD_LOGIC_VECTOR (4 downto 0);
signal s_almacen: STD_LOGIC_VECTOR (3 downto 0);
signal s_opcion: STD_LOGIC_VECTOR (2 downto 0);
signal s_data_in, s_data_out: STD_LOGIC_VECTOR (7 downto 0);
signal s_envio_completado, s_datos_correctos,  s_recibido, s_bidali: STD_LOGIC;
signal s_kat0, s_kat1, s_kat2, s_kat3: STD_LOGIC_VECTOR (6 downto 0);

begin
cts<='Z';
rts<='Z';
JA(0)<='Z';
JA(1)<='Z';
JA(2)<='Z';
JA(3)<='Z';
me_princ: TOP_Principal port map(rst=>rst, clk=>clk, sw=>sw, recibir=>recibir, sacar=>sacar, confirmar=>confirmar, denegar=>denegar,
 cantidad=>s_cantidad,producto=>s_producto,empresa=>s_empresa,almacen=>s_almacen, sel=>s_opcion, 
 data_out=>s_data_in, envio_completado => s_envio_completado, datos_correctos=> s_data_out,
 recibido=> s_recibido, bidali=>s_bidali);

covertidor: top_convertidor port map(clk=>clk, empresa=>s_empresa, opcion=>s_opcion, cantidad=>s_cantidad, producto=>s_producto, almacen=>s_almacen,
 kat0=>s_kat0,kat1=>s_kat1,kat2=>s_kat2,kat3=>s_kat3);

display: ME_display port map(clk=>clk, rst=>rst, kat0=>s_kat0, kat1=>s_kat1,kat2=>s_kat2,kat3=>s_kat3, 
punto=>punto, seg=>katodo, an=>anodo);

linea_serie: top_serial port map(clk=>clk, completado=>s_envio_completado, data_in=>s_data_in, data_out=>s_data_out,
serial_in=> serial_in, serial_out=>serial_out,recibido=>s_recibido, bidali=>s_bidali);

end Behavioral;


