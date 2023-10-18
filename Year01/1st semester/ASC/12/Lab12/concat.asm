bits 32       

extern _printf

global _afisareASM

segment data public data use32 
    format db "%d ",0
    sir1 times 10 dd 0
    

segment code public code use32 

_afisareASM:
        push EBP
        mov EBP,ESP
        ;pushad
        
        ;sub esp, 4 * 3
        
        ;add ESP, 4*1
        
        mov ECX,[ESP+8]
        mov ESI,[ESP+12]
        
        lodsd
        et1:
            lodsd
            push ECX
            ;printf("%d",EAX)
            push EAX
            push dword format 
            call _printf
            add ESP,4*2
            pop ECX
        loop et1
        
        mov EAX,0   
        
        ;popad
        mov ESP,EBP
        pop EBP
        ret
