----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 28.12.2020 19:21:49
-- Design Name: 
-- Module Name: traductor_empresa - Behavioral
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

entity traductor_empresa is
    Port ( sel : in STD_LOGIC_VECTOR (4 downto 0);
       emp0, emp1, emp2: out STD_LOGIC_VECTOR(4 downto 0));
end traductor_empresa;

architecture Behavioral of traductor_empresa is

begin

WITH sel SELECT 
emp0 <= "00101" WHEN "00000", --Fhg
        "00001" WHEN "00001",--Bru
        "00100" WHEN "00010",--Eml
        "10000" WHEN "00011",--Ppc
        "00100" WHEN "00100",--Esc
        "01100" WHEN "00101",--Mug
        "00001" WHEN "00110", --Bic
        "10010" WHEN "00111", --Rsc
        "01011" WHEN "01000", --Lmr
        "00000" WHEN "01001", --Ahi
        "00001" WHEN "01010", --Bdp
        "01000" WHEN "01011", --Ibp
        "01011" WHEN "01100", --Len
        "00101" WHEN "01101", --Fen
        "01100" WHEN "01110", --Mic
        "00101" WHEN "01111", --Fcn
        "00100" WHEN "10000", --Efa
        "00100" WHEN "10001", --Efe
        "00100" WHEN "10010", --Elr
        "00000" WHEN "10011", --Aya
        "10000" WHEN "10100", --Pcc
        "01100" WHEN "10101", --Mdm
        "10000" WHEN "10110", --Pcb
        "10010" WHEN "10111", --Rez 
        "00001" WHEN "11000", --Bnn
        "01100" WHEN "11001", --Mcl
        "01011" WHEN "11010", --Lnp
        "00011" WHEN "11011", --Dmp 
        "10010" WHEN "11100", --Rtg
--        "01010" WHEN "11101", --
--        "01010" WHEN "11110", --      
        "01010" when others;
        
WITH sel SELECT 
        emp1 <= "00111" WHEN "00000", --fHg
                "10010" WHEN "00001",--bRu
                "01100" WHEN "00010",--eMl
                "10000" WHEN "00011",--pPc
                "10011" WHEN "00100",--eSc
                "10101" WHEN "00101",--mUg
                "01000" WHEN "00110", --bIc
                "10011" WHEN "00111", --rSc
                "01100" WHEN "01000", --lMr
                "00111" WHEN "01001", --aHi
                "00011" WHEN "01010", --bDp
                "00001" WHEN "01011", --iBp
                "00100" WHEN "01100", --lEn
                "00100" WHEN "01101", --fEn
                "01000" WHEN "01110", --mIc
                "00010" WHEN "01111", --fCn
                "00101" WHEN "10000", --eFa
                "00101" WHEN "10001", --eFe
                "01011" WHEN "10010", --eLr
                "11001" WHEN "10011", --aYa
                "00010" WHEN "10100", --pCc
                "00011" WHEN "10101", --mDm
                "00010" WHEN "10110", --pCb
                "00100" WHEN "10111", --rEz 
                "01101" WHEN "11000", --bNn
                "00010" WHEN "11001", --mCl
                "01101" WHEN "11010", --lNp
                "01100" WHEN "11011", --dMp 
                "10100" WHEN "11100", --rTg
        --        "01010" WHEN "11101", --
        --        "01010" WHEN "11110", --      
                "01010" when others;
     
WITH sel SELECT 
        emp2 <= "00110" WHEN "00000", --fhG
                "10101" WHEN "00001",--brU
                "01011" WHEN "00010",--emL
                "00010" WHEN "00011",--ppC
                "00010" WHEN "00100",--esC
                "00110" WHEN "00101",--muG
                "00010" WHEN "00110", --biC
                "00010" WHEN "00111", --rsC
                "10010" WHEN "01000", --lmR
                "01000" WHEN "01001", --ahI
                "10000" WHEN "01010", --bdP
                "10000" WHEN "01011", --ibP
                "01101" WHEN "01100", --leN
                "01101" WHEN "01101", --feN
                "00010" WHEN "01110", --miC
                "01101" WHEN "01111", --fcN
                "00000" WHEN "10000", --efA
                "00100" WHEN "10001", --efE
                "10010" WHEN "10010", --elR
                "00000" WHEN "10011", --ayA
                "00010" WHEN "10100", --pcC
                "01100" WHEN "10101", --mdM
                "00001" WHEN "10110", --pcB
                "11010" WHEN "10111", --reZ 
                "01101" WHEN "11000", --bnN
                "01011" WHEN "11001", --mcL
                "10000" WHEN "11010", --lnP
                "10000" WHEN "11011", --dmP 
                "00110" WHEN "11100", --rtG
        --        "01010" WHEN "11101", --
        --        "01010" WHEN "11110", --      
                "01010" when others;


end Behavioral;
