bits 32 
global construct 

segment code use32=code public

construct:

;[ESP]<-adresa de revenire
;[ESP+4]<-adresa lui s
;[ESP+8]<-lungimea lui s
;[ESP+12]<-adresa lui n
;[ESP+16]<-adresa lui p

cld
mov ESI,[ESP+4]
mov EBX,0;lungimea lui n
mov EDX,0;lungimea lui p

mov ECX,[ESP+8] ;punem in ECX lungimea lui s
for:

    lodsw ;AX<-elemntul actual

    cmp AX,0
    jge este_pozitiv

    mov EDI,[ESP+12]
    add EDI,EBX
    add EDI,EBX
    stosw
    inc EBX
    
    jmp fin ; sa nu se execute celelelate instructiuni,de doreste o structura if else
    
    este_pozitiv:
    mov EDI,[ESP+16]
    add EDI,EDX
    add EDI,EDX
    stosw
    inc EDX
    
    fin:
    
loop for

ret