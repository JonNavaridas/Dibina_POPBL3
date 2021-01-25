----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 18.12.2020 16:00:05
-- Design Name: 
-- Module Name: TOP_Principal - Behavioral
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

entity TOP_Principal is
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
end TOP_Principal;

architecture Behavioral of TOP_Principal is
type etapa is (rec_avisar, sac_avisar,
rec_producto,rec_cantidad, rec_empresa,
rec_enviar_producto, rec_enviar_cantidad,rec_pausa_can,rec_enviar_cantidad2, rec_enviar_empresa, 
enviar_almacen,sac_enviar_producto, sac_enviar_cantidad,sac_pausa_can,sac_enviar_cantidad2, sac_enviar_empresa,
sac_almacen, sac_producto,sac_cantidad, sac_empresa,
esperando,pausa_fin,enviar_final,esp_resp,introducir_mas,reset_mas,
correcto, incorrecto);
signal actual, siguiente: etapa;
shared variable tiempo,cont: integer;
shared variable rst_temp:std_logic;
begin



SEQ: process(clk,rst)--egoerak aktualizatzeko prozesua
begin
if rst='1' then
    actual<=esperando;
elsif clk'event and clk='1' then
    actual<=siguiente;
end if;
end process;

COMB: process(clk,actual,recibir,sacar,confirmar,denegar,sw)--Dos ramas una para recibir un producto y otra para sacar un pedido
begin
case actual is
when esperando=>
    rst_temp:='0';
    sel<="101";
    bidali<='0';   
    if recibir='1' and tiempo>2 then
        siguiente<=rec_avisar;
    elsif sacar='1' and tiempo>2 then
        siguiente<=sac_avisar;
    else
        siguiente<=actual;
    end if;
 when rec_avisar=>
    rst_temp:='1';
    bidali<='1';
    data_out<="10000001";
    if envio_completado='1' then
        siguiente<=rec_producto;
    else
        siguiente<=actual;
    end if;   
 when sac_avisar=>
    rst_temp:='1';
       bidali<='1';
       data_out<="10000010";
       if envio_completado='1' then
           siguiente<=sac_almacen;
       else
           siguiente<=actual;
       end if;

--RECIBIR
when rec_producto=>
    rst_temp:='0';
    sel<="000";
    bidali<='0';
    producto<=sw(4 downto 0);
     
    if confirmar='1' and tiempo>4 then
        siguiente<=rec_enviar_producto;
    else
        siguiente<=actual;
    end if;
when rec_enviar_producto=>
    rst_temp:='1';
    data_out<= "000" & sw(4 downto 0);
    bidali<='1';
    if envio_completado='1' then
        siguiente<=rec_cantidad;
    else 
        siguiente<=actual;
    end if; 
       
when rec_cantidad=>
    rst_temp:='0';
    sel<="001";
    bidali<='0';
    cantidad<=sw;

    if confirmar='1' and tiempo>4 then
        siguiente<=rec_enviar_cantidad;
    else
        siguiente<=actual;
    end if;
when rec_enviar_cantidad=>
    rst_temp:='1';
    data_out<="00000" & sw(9 downto 7);
    bidali<='1';
    if envio_completado='1'  then
        siguiente<=rec_pausa_can;
    else
        siguiente<=actual;
    end if;
when rec_pausa_can=>
    rst_temp:='0';
    bidali<='0';
    sel<="001";
    if tiempo=1 then
        siguiente<=rec_enviar_cantidad2;
    else
        siguiente<=actual;
    end if;
when rec_enviar_cantidad2=>
    rst_temp:='1';
    data_out<="0"& sw(6 downto 0);
    bidali<='1';
    if envio_completado='1' then
        siguiente<=rec_empresa;
    else
        siguiente<=actual;
    end if;      
when rec_empresa=>
    rst_temp:='0';
    bidali<='0';
    empresa<=sw(4 downto 0);
    sel<="010";
    if confirmar='1' and tiempo>4 then
        siguiente<=rec_enviar_empresa;
    else
        siguiente<=actual;
    end if;
    
when rec_enviar_empresa=>
    rst_temp:='1';
    data_out<="000" & sw(4 downto 0);
    bidali<='1';
    if envio_completado='1' then
        siguiente<=pausa_fin;
    else
        siguiente<=actual;
    end if;
    
--SACAR
when sac_almacen=>
    rst_temp:='0';
    bidali<='0';
    almacen<=sw(3 downto 0);
    sel<="110";
    if confirmar='1' and tiempo>4 then
        siguiente<=enviar_almacen;
    else
        siguiente<=actual;
    end if;
when enviar_almacen=>
    rst_temp:='1';
    data_out<="0000" & sw(3 downto 0);
    bidali<='1';
    if envio_completado='1' then
        siguiente<=sac_producto;
    else
        siguiente<=actual;
    end if;
when sac_producto=>
    rst_temp:='0';
    sel<="000";
    bidali<='0';
    producto<=sw(4 downto 0);
    if confirmar='1' and tiempo>4 then
        siguiente<=sac_enviar_producto;
    else
        siguiente<=actual;
    end if;
when sac_enviar_producto=>
    rst_temp:='1';
    data_out<= "000" & sw(4 downto 0);
    bidali<='1';
    if envio_completado='1' then
         siguiente<=sac_cantidad;
    else 
        siguiente<=actual;
    end if;
when sac_cantidad=>
    rst_temp:='0';
    sel<="001";
    bidali<='0';
    cantidad<=sw;   
    if confirmar='1' and tiempo>4 then
        siguiente<=sac_enviar_cantidad;
    else
        siguiente<=actual;
    end if;
when sac_enviar_cantidad=>
    rst_temp:='1';
    data_out<="00000" & sw(9 downto 7);
    bidali<='1';
    if envio_completado='1'  then
        siguiente<=sac_pausa_can;
    else
        siguiente<=actual;
    end if;
when sac_pausa_can=>
    rst_temp:='0';
    bidali<='0';
    sel<="001";
    if tiempo=1 then
        siguiente<=sac_enviar_cantidad2;
    else
        siguiente<=actual;
    end if;
when sac_enviar_cantidad2=>
    rst_temp:='1';
    data_out<="0"& sw(6 downto 0);
    bidali<='1';
    if envio_completado='1' then
        siguiente<=sac_empresa;
    else
        siguiente<=actual;
    end if;      
 when sac_empresa=>
    rst_temp:='0';
    bidali<='0';
    empresa<=sw(4 downto 0);
    sel<="010";
    if confirmar='1' and tiempo>4 then
        siguiente<=sac_enviar_empresa;
    else
        siguiente<=actual;
    end if;
when sac_enviar_empresa=>
    rst_temp:='1';
    data_out<="000" & sw(4 downto 0);
    bidali<='1';
    if envio_completado='1' then
        siguiente<=introducir_mas;
    else
        siguiente<=actual;
    end if;
--VALIDACIONES Y ENVIAR    
when introducir_mas=>
    rst_temp:='0';
    bidali<='0';
    sel<="111";
    if denegar='1' and tiempo>3 then
            siguiente<=enviar_final;
    elsif confirmar='1' and tiempo>3 then
            siguiente<=reset_mas;  
    else
            siguiente<=actual;
    end if;
when reset_mas=>
    rst_temp:='1';
    bidali<='0';
    sel<="111";
    siguiente<=sac_producto;
when pausa_fin=>
    rst_temp:='0';
    bidali<='0';
    sel<="101";
    if tiempo=1 then
        siguiente<=enviar_final;
    else
        siguiente<=actual;
    end if; 
when enviar_final=> 
    rst_temp:='1';
    sel<="101";
    data_out<="11111111";
    bidali<='1';
    if envio_completado='1' then
        siguiente<=esp_resp;
    else
        siguiente<=actual;
    end if;
when esp_resp=>
    rst_temp:='1';
    sel<="101";
    bidali<='0';
    if datos_correctos="00000001" and recibido='1' then
            siguiente<=correcto; 
    elsif datos_correctos="00000000" and recibido='1' then
        siguiente<=incorrecto; 
        else
            siguiente<=actual;
        end if; 
when correcto=> 
    rst_temp:='0';
    bidali<='0'; 
    sel<="011";  
    if tiempo=8 then
            siguiente<=esperando; 
        else
            siguiente<=actual;
        end if;        
when incorrecto=>
    rst_temp:='0';
    bidali<='0';  
    sel<="100";  
    if tiempo=8 then
            siguiente<=esperando; 
        else
            siguiente<=actual;
        end if;            
when others=> 
    siguiente<=esperando;
end case;
end process;

contador_segundos: process(clk, rst, actual)--denbora kontrolatzen duen prozesua rst_temp 0ra dagoenean kontatzen du eta 1era dagoenean 0ra itzuli
begin
if rst='1' then
    cont:=0;
    tiempo:=0;
elsif clk'event and clk='1' then
    if rst_temp='1' then
        tiempo:=0;
        cont:=0;
    else
        if cont>=24999999 then--segundu laurdena  simulaziorako 2ra jarri
            cont:=0;
            tiempo:=tiempo+1;
        else
            cont:=cont+1;
        end if;
     end if;
end if;
end process;

--recibir_sacar_proc: process(clk, actual)
--begin
--     if actual=rec_avisar  then--or actual=rec_producto or actual=rec_cantidad or actual=rec_empresa
--       rec_sac:='0';
--    end if;
--     if actual=sac_avisar  then--or actual=sac_producto or actual=sac_cantidad or actual=sac_empresa or actual=sac_almacen
--        rec_sac:='1';
--     end if;
--end process;

end Behavioral;
