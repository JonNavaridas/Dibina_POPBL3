----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 29.12.2020 11:34:38
-- Design Name: 
-- Module Name: top_convertidor - Behavioral
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

entity top_convertidor is
    Port ( clk : in STD_LOGIC;
           almacen : in STD_LOGIC_VECTOR (3 downto 0);
           empresa : in STD_LOGIC_VECTOR (4 downto 0);
           opcion : in STD_LOGIC_VECTOR (2 downto 0);
           cantidad : in STD_LOGIC_VECTOR (9 downto 0);
           producto : in STD_LOGIC_VECTOR (4 downto 0);
           kat0, kat1, kat2, kat3: out STD_LOGIC_VECTOR(6 downto 0));
end top_convertidor;

architecture Behavioral of top_convertidor is

component abecedario is
    Port ( sel : in STD_LOGIC_VECTOR (4 downto 0);
       letra : out STD_LOGIC_VECTOR (6 downto 0));
end component;

component bin_decimal is
    Port ( clk : in STD_LOGIC;
           bin : in STD_LOGIC_VECTOR (11 downto 0);
           dec_0 : out STD_LOGIC_VECTOR (3 downto 0);
           dec_1 : out STD_LOGIC_VECTOR (3 downto 0);
           dec_2 : out STD_LOGIC_VECTOR (3 downto 0));
end component;

component mux is
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
end component;

component cantidad_display is
    Port ( cantidad_in : in STD_LOGIC_VECTOR (3 downto 0);
           cantidad_out : out STD_LOGIC_VECTOR (6 downto 0));
end component;

component traductor_empresa is
    Port ( sel : in STD_LOGIC_VECTOR (4 downto 0);
       emp0, emp1, emp2: out STD_LOGIC_VECTOR(4 downto 0));
end component;

component traductor_producto is
    Port ( sel : in STD_LOGIC_VECTOR (4 downto 0);
           prod0, prod1, prod2: out STD_LOGIC_VECTOR(4 downto 0));
end component;

signal s_cantidad, s_almacen: STD_LOGIC_VECTOR(11 downto 0);
signal s_almacen0, s_almacen1, s_almacen2: STD_LOGIC_VECTOR(3 downto 0);
signal s_cantidad0, s_cantidad1, s_cantidad2: STD_LOGIC_VECTOR(3 downto 0);
signal s_producto0, s_producto1, s_producto2, s_empresa0, s_empresa1, s_empresa2: STD_LOGIC_VECTOR(4 downto 0);
signal almac0, almac1, almac2, cant0,cant1,cant2, produc0,produc1,produc2, empr0,empr1,empr2: STD_LOGIC_VECTOR(6 downto 0);

begin

s_cantidad<= "00" & cantidad;
s_almacen<= "00000000" & almacen;
bin_dec0: bin_decimal port map(clk=> clk, bin=> s_almacen, dec_0=> s_almacen0, dec_1=> s_almacen1,dec_2=> s_almacen2);
bin_dec1: bin_decimal port map(clk=> clk, bin=> s_cantidad, dec_0=> s_cantidad0, dec_1=> s_cantidad1,dec_2=> s_cantidad2);

trad_empr: traductor_empresa port map(sel=> empresa, emp0=> s_empresa0, emp1=> s_empresa1,emp2=> s_empresa2);
trad_produc: traductor_producto port map(sel=> producto, prod0=> s_producto0, prod1=> s_producto1, prod2=> s_producto2);

abc_emp0: abecedario port map(sel=>s_empresa0, letra=> empr0);
abc_emp1: abecedario port map(sel=>s_empresa1, letra=> empr1);
abc_emp2: abecedario port map(sel=>s_empresa2, letra=> empr2);

abc_produc0: abecedario port map(sel=>s_producto0, letra=> produc0);
abc_produc1: abecedario port map(sel=>s_producto1, letra=> produc1);
abc_produc2: abecedario port map(sel=>s_producto2, letra=> produc2);

cant_disp0: cantidad_display port map(cantidad_in=> s_cantidad0, cantidad_out=> cant0);
cant_disp1: cantidad_display port map(cantidad_in=> s_cantidad1, cantidad_out=> cant1);
cant_disp2: cantidad_display port map(cantidad_in=> s_cantidad2, cantidad_out=> cant2);

cant_disp3: cantidad_display port map(cantidad_in=> s_almacen0, cantidad_out=> almac0);
cant_disp4: cantidad_display port map(cantidad_in=> s_almacen1, cantidad_out=> almac1);
cant_disp5: cantidad_display port map(cantidad_in=> s_almacen2, cantidad_out=> almac2);

mux0: mux port map(SEL=> opcion, cantidad=>cant0, producto=> produc0, empresa=> empr0, bien=>"1111001", mal=>"1001000", vacio=>"0111111", almacen=>almac0, mas=>"1001000", IRT=> kat1);
mux1: mux port map(SEL=> opcion, cantidad=>cant1, producto=> produc1, empresa=> empr1, bien=>"0000110", mal=>"0001000", vacio=>"0111111", almacen=>almac1, mas=>"0001000", IRT=> kat2);
mux2: mux port map(SEL=> opcion, cantidad=>cant2, producto=> produc2, empresa=> empr2, bien=>"0101011", mal=>"1000111", vacio=>"0111111", almacen=>almac2, mas=>"0010010", IRT=> kat3);
muxEsp: mux port map(SEL=> opcion, cantidad=>"1000110", producto=>"0001100" , empresa=> "0000110", bien=>"0000011", mal=>"1111111", vacio=>"0111111", almacen=>"0001000", mas=>"1111111", IRT=> kat0);
end Behavioral;
