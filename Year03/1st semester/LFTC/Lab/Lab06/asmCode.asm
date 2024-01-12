bits 32
global start

extern exit
import exit msvcrt.dll

extern scanf
import scanf msvcrt.dll

extern printf
import printf msvcrt.dll

segment data use32 class=data
	 b times 4 db 0
	 a times 4 db 0
	 format db "%d", 0

segment code use32 class=code
	start:
		push dword a
		push dword format
		call [scanf]
		add ESP, 4 * 2

		mov AL, [a]
		mov AH, 0
		mov DL, 2
		div DL
		add AL, 3
		mov [b], AL

		push dword [b]
		push dword format
		call [printf]
		add ESP, 4 * 2

		push dword 0
		call [exit]
