		. = 0x200
		.BLKW	0x100, DAT
DATA:
		.WORD	1, 2, 3, FIB, ONE1, 5	; 200 0001 0002 0003 020e 022f 0005
FIB2	= DATA1
DATA1	= DATA2
DATA2	= 0x99
; this
BEG:
		MOV #5, (SP)+  kda	; 206 43fe 0005
		JSR FIB				; 208 b3fe 020e
		SUB #1, SP			; 20a 63e6 0001
		MOV R4, (R0)		; 20c 4088
		HLT					; 20d 0000
FIB:
		MOV R1, (SP)+		; 20e 403e
		MOV R2, (SP)+		; 20f 405e
		MOV R3, (SP)+		; 210 407e
		MOV SP, R1			; 211 40c1
		SUB #5, R1			; 212 63e1 0005
		MOV (R1), R2		; 214 4122
		CMP #1, R2			; 215 6fe2 0001
		BRZ ONE				; 217 c7e7 022f
		CMP #2, R2			; 219 6fe2 0002
		BRZ ONE				; 21b c7e7 022f	/////
		SUB #1, R2			; 21d 63e2 0001
		MOV R2, (SP)+		; 21f 405e
		RJS FIB				; 220 d7fe ffec
		SUB #1, SP			; 222 63e6 0001
		MOV R4, R3			; 224 4083
		SUB #1, R2			; 225 63e2 0001
		MOV R2, (SP)+		; 227 405e
		JSR FIB				; 228 b3fe 020e
		SUB #1, SP			; 22a 63e6 0001
		ADD R3, R4			; 22c 5064
		JMP RETURN			; 22d 47e7 0231
ONE:
		MOV #1, R4			; 22f 43e4 0001
RETURN:
		MOV -(SP), R3		; 231 42c3
		MOV -(SP), R2		; 232 42c2
		MOV -(SP), R1		; 233 42c1
		RET					; 234 4ac7
;		RET
		ASL R2				; 235 2002
		LSR (r4)			; 236 340c
		RBC RETURN			; 237 efe7 fff8
;		RBC DATA			; 237 efe7 fff8
;		MOV (R4), -(R0)
;		RJP FIB
		.END fib				; 235
;		jj RET
;		kk RET
