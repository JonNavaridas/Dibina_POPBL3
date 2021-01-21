----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 17.12.2020 17:40:14
-- Design Name: 
-- Module Name: traductor_producto - Behavioral
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

entity traductor_producto is
    Port ( sel : in STD_LOGIC_VECTOR (4 downto 0);
           prod0, prod1, prod2: out STD_LOGIC_VECTOR(4 downto 0));
end traductor_producto;

architecture Behavioral of traductor_producto is

begin

WITH sel SELECT 
prod0<= "00001" WHEN "00000", --Bol
        "10010" WHEN "00001",--Rot
        "10000" WHEN "00010",--Pil
        "00001" WHEN "00011",--Bor
        "10100" WHEN "00100",--Tor
        "01011" WHEN "00101",--Led
        "10100" WHEN "00110", --Tra
        "10010" WHEN "00111", --Res
        "00010" WHEN "01000", --Cab
        "00010" WHEN "01001", --Con
        "10100" WHEN "01010", --Tur
        "00000" WHEN "01011", --Ada
        "10110" WHEN "01100", --Vol
        "00000" WHEN "01101", --Ara
        "10000" WHEN "01110", --Pap
        "10000" WHEN "01111", --Por
        "00111" WHEN "10000", --Hdm
        "10110" WHEN "10001", --Vga
        "00000" WHEN "10010", --Avh
        "10100" WHEN "10011", --Tip
        "00010" WHEN "10100", --Cua--
        "01100" WHEN "10101", --Mon
        "10100" WHEN "10110", --Tec
        "00000" WHEN "10111", --Aur 
        "10011" WHEN "11000", --Sby
        "01100" WHEN "11001", --Mtr
        "00010" WHEN "11010", --Clp
        "10000" WHEN "11011", --Pst 
        "00000" WHEN "11100", --Ard 
        "10010" WHEN "11101", --Rpb 
        "00001" WHEN "11110", --Bat       
        "01010" when others;
        
WITH sel SELECT         
prod1<= "01111" WHEN "00000", --bOl
        "01111" WHEN "00001",--rOt
        "01000" WHEN "00010",--pIl
        "01111" WHEN "00011",--bOr
        "01111" WHEN "00100",--tOr
        "00100" WHEN "00101",--lEd
        "10010" WHEN "00110", --tRa
        "00100" WHEN "00111", --rEs
        "00000" WHEN "01000", --cAb
        "01111" WHEN "01001", --cOn
        "10101" WHEN "01010", --tUr
        "00011" WHEN "01011", --aDa
        "01111" WHEN "01100", --vOl
        "10010" WHEN "01101", --aRa
        "00000" WHEN "01110", --pAp
        "01111" WHEN "01111", --pOr
        "00011" WHEN "10000", --hDm
        "00110" WHEN "10001", --vGa
        "10110" WHEN "10010", --aVh
        "01000" WHEN "10011", --tIp
        "10101" WHEN "10100", --cUa--
        "01111" WHEN "10101", --mOn
        "00100" WHEN "10110", --tEc
        "10101" WHEN "10111", --aUr 
        "00001" WHEN "11000", --sBy
        "01011" WHEN "11010", --mTr
        "10100" WHEN "11001", --cLp
        "10011" WHEN "11011", --pSt 
        "10010" WHEN "11100", --aRd 
        "10000" WHEN "11101", --rPb 
        "00000" WHEN "11110", --bAt
        "01010" when others;

WITH sel SELECT         
prod2<= "01011" WHEN "00000", --boL
        "10100" WHEN "00001",--roT
        "01011" WHEN "00010",--piL
        "10010" WHEN "00011",--boR
        "10010" WHEN "00100",--toR
        "00011" WHEN "00101",--leD
        "00000" WHEN "00110", --trA
        "10011" WHEN "00111", --reS
        "00001" WHEN "01000", --caB
        "01101" WHEN "01001", --coN
        "10010" WHEN "01010", --tuR
        "00000" WHEN "01011", --adA
        "01011" WHEN "01100", --voL
        "00000" WHEN "01101", --arA
        "10000" WHEN "01110", --paP
        "10010" WHEN "01111", --poR
        "01100" WHEN "10000", --hdM
        "00000" WHEN "10001", --vgA
        "00111" WHEN "10010", --avH
        "10000" WHEN "10011", --tiP
        "00000" WHEN "10100", --cuA
        "01101" WHEN "10101", --moN
        "00010" WHEN "10110", --teC
        "10010" WHEN "10111", --auR 
        "11001" WHEN "11000", --sbY
        "10010" WHEN "11001", --mtR
        "10000" WHEN "11010", --clP
        "10100" WHEN "11011", --psT 
        "00011" WHEN "11100", --arD 
        "00001" WHEN "11101", --rpB 
        "10100" WHEN "11110", --baT
        "01010" when others;

end Behavioral;
