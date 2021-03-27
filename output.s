	.text
;------------------------
	.globl	__init__
	.type	__init__, @function
__init__:
__init__.entrance:
__init__.exit:
__init__.ret:
	.size	__init__, .-__init__
;------------------------
	.globl	main
	.type	main, @function
main:
main.entrance:
	li t0, 10
	sw t0, 4(sp)
	lw t0, 8(sp)
	lw t1, 4(sp)
	sw t1, 0(t0)
	li t0, 0
	sw t0, 12(sp)
	lw t0, 16(sp)
	lw t1, 12(sp)
	sw t1, 0(t0)
	li t0, 1
	sw t0, 20(sp)
	lw t0, 24(sp)
	lw t1, 20(sp)
	sw t1, 0(t0)
	lw t1, 32(sp)
	lw t0, 0(t1)
	sw t0, 28(sp)
	li t0, 1
	sw t0, 36(sp)
	lw t0, 32(sp)
	lw t1, 36(sp)
	sw t1, 0(t0)
	j for_condition
for_condition:
	lw t0, 0(t1)
	sw t0, 40(sp)
	lw t1, 8(sp)
	lw t0, 0(t1)
	sw t0, 44(sp)
	lw t1, 40(sp)
	lw t2, 44(sp)
	slt t0, t1, t2
	sw t0, 48(sp)
	lw t0, 48(sp)
	bnez t0, for_body
	j after_for
for_body:
	lw t0, 0(t1)
	sw t0, 52(sp)
	lw t1, 16(sp)
	lw t0, 0(t1)
	sw t0, 60(sp)
	lw t1, 24(sp)
	lw t0, 0(t1)
	sw t0, 64(sp)
	lw t1, 60(sp)
	lw t2, 64(sp)
	add t0, t1, t2
	sw t0, 68(sp)
	lw t0, 56(sp)
	lw t1, 68(sp)
	sw t1, 0(t0)
	lw t1, 16(sp)
	lw t0, 0(t1)
	sw t0, 72(sp)
	lw t1, 24(sp)
	lw t0, 0(t1)
	sw t0, 76(sp)
	lw t0, 16(sp)
	lw t1, 76(sp)
	sw t1, 0(t0)
	lw t1, 24(sp)
	lw t0, 0(t1)
	sw t0, 80(sp)
	lw t1, 56(sp)
	lw t0, 0(t1)
	sw t0, 84(sp)
	lw t0, 24(sp)
	lw t1, 84(sp)
	sw t1, 0(t0)
	j for_incr
for_incr:
	lw t0, 0(t1)
	sw t0, 88(sp)
	lw t1, 88(sp)
	addi t0, t1, 1
	sw t0, 92(sp)
	lw t0, 32(sp)
	lw t1, 92(sp)
	sw t1, 0(t0)
	j for_condition
after_for:
	lw t0, 0(t1)
	sw t0, 96(sp)
	lw t0, 100(sp)
	lw t1, 96(sp)
	sw t1, 0(t0)
	j main.exit
main.exit:
	j main.ret
main.ret:
	ret
	.size	main, .-main
