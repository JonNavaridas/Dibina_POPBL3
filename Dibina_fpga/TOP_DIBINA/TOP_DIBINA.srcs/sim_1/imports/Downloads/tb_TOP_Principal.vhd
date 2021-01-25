----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 19.01.2021 13:45:32
-- Design Name: 
-- Module Name: tb_TOP_Principal - Behavioral
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

entity tb_TOP_Principal is
--  Port ( );
end tb_TOP_Principal;

architecture Behavioral of tb_TOP_Principal is

component TOP_Principal
    Port ( rst : in STD_LOGIC;
           clk : in STD_LOGIC;
           sw : in STD_LOGIC_VECTOR (9 downto 0);
           recibir : in STD_LOGIC; --w19
           sacar : in STD_LOGIC; --t17
           confirmar : in STD_LOGIC; --t18
           denegar : in STD_LOGIC; --u17
           datos_correctos: in STD_LOGIC_VECTOR (7 downto 0);
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

signal rst,clk,recibir,sacar,confirmar,denegar,envio_completado,recibido, bidali: STD_LOGIC;
signal sel: STD_LOGIC_VECTOR (2 downto 0);
signal almacen: STD_LOGIC_VECTOR (3 downto 0);
signal empresa, producto: STD_LOGIC_VECTOR(4 downto 0);
signal datos_correctos, data_out: STD_LOGIC_VECTOR (7 downto 0);
signal sw, cantidad: STD_LOGIC_VECTOR (9 downto 0);

begin

dut: TOP_Principal port map (clk=>clk,rst=>rst,recibir=>recibir, sacar=>sacar, confirmar=>confirmar,
                 denegar=>denegar,envio_completado=>envio_completado, recibido=>recibido,
                 bidali=>bidali,sel=>sel,almacen=>almacen,
                 empresa=>empresa,producto=>producto,datos_correctos=>datos_correctos,
                 data_out=>data_out,sw=>sw,cantidad=>cantidad);
              
erlojua: process 
begin
clk<='0';
wait for 1ns;
clk<='1';
wait for 1ns;
end process;

stim_proc: process
begin
wait for 2ns;
rst<='1';
wait for 2ns;
rst<='0';
wait for 20ns;
recibir<='1'; -------------------------------------------EMPEZAR A RECIBIR
wait for 20ns;
recibir<='0';
envio_completado<='1'; --rec_avisar   
wait for 20ns;
envio_completado<='0';
sw<="0000011001"; --rec_producto   
wait for 20ns;
confirmar<='1';
wait for 20ns;
confirmar<='0';
envio_completado<='1'; --enviar_producto   
wait for 20ns;
envio_completado<='0'; 
sw<="0010011001"; --rec_cantidad 
wait for 20ns;
confirmar<='1';
wait for 20ns;
confirmar<='0';
envio_completado<='1'; --enviar_cantidad
wait for 20ns; --rec_pausa_cantidad
envio_completado<='1'; --enviar_cantidad2    
wait for 20ns;
envio_completado<='0';
sw<="0000011001"; --rec_empresa 
wait for 20ns;
confirmar<='1';
wait for 20ns; 
confirmar<='0';
envio_completado<='1'; --enviar_empresa  
wait for 20ns; --pausa_fin
envio_completado<='1'; --enviar_final
wait for 20ns;
datos_correctos<="00000001"; --esp_resp
recibido<='1';
wait for 20ns; --correcto, if tiempo>4 -> esperando
recibido<='0';
sacar<='1'; -----------------------------------------------EMPEZAR A SACAR
wait for 60ns;
sacar<='0';
envio_completado<='1'; --sac_avisar
wait for 20ns;
envio_completado<='0'; 
sw<="0000000100"; --rec_almacen  
wait for 20ns;
confirmar<='1';
wait for 20ns;
confirmar<='0';
envio_completado<='1'; --enviar_almacen
wait for 20ns;
envio_completado<='0';
sw<="0000001000"; --sac_producto 
wait for 20ns;
confirmar<='1';  
wait for 20ns;
confirmar<='0';  
envio_completado<='1'; --enviar_producto   
wait for 20ns;
envio_completado<='0';
sw<="0010011111"; --sac_cantidad 
wait for 20ns; 
confirmar<='1';
wait for 20ns;
confirmar<='0';
envio_completado<='1'; --enviar_cantidad
wait for 20ns; --rec_pausa_cantidad
envio_completado<='1'; --enviar_cantidad2 
wait for 20ns;
envio_completado<='0';
sw<="0000010000"; --sac_empresa 
wait for 20ns;
confirmar<='1';
wait for 20ns; 
confirmar<='0';
envio_completado<='1'; --enviar_empresa 
wait for 20ns; 
envio_completado<='0'; 
confirmar<='1'; --introducir mas???????
wait for 20ns;
confirmar<='0'; 
sw<="0000010101"; --sac_producto 
wait for 20ns;
confirmar<='1';  
wait for 20ns;
confirmar<='0'; 
envio_completado<='1'; --enviar_producto   
wait for 20ns;
envio_completado<='0';
sw<="0010000101"; --sac_cantidad 
wait for 20ns; 
confirmar<='1';
wait for 20ns;
confirmar<='0';
envio_completado<='1'; --enviar_cantidad
wait for 20ns; --rec_pausa_cantidad
envio_completado<='1'; --enviar_cantidad2 
wait for 20ns;
envio_completado<='0';
sw<="0000001001"; --sac_empresa 
wait for 20ns;
confirmar<='1';
wait for 20ns; 
confirmar<='0';
envio_completado<='1'; --enviar_empresa 
wait for 20ns; 
envio_completado<='0';
denegar<='1'; --introducir mas???????
wait for 20ns;
denegar<='0'; 
envio_completado<='1'; --enviar_final
wait for 20ns;
envio_completado<='0';
datos_correctos<="00000001"; --esp_resp
recibido<='1';
wait for 20ns; --correcto, if tiempo>4 -> esperando
recibido<='0';
wait;
end process;


end Behavioral;
