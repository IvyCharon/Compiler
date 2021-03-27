	.text
;------------------------
	.globl	__init__
	.type	__init__, @function
__init__:
__init__.entrance:
	.size	__init__, .-__init__
;------------------------
	.globl	main
	.type	main, @function
main:
main.entrance:
	li %0, 10
	sw %0, 0(%1)
	li %2, 0
	sw %2, 0(%3)
	li %4, 1
	sw %4, 0(%5)
	lw %6, 0(%7)
	li %8, 1
	sw %8, 0(%7)
	j for_condition
for_condition:
	lw %9, 0(%7)
	lw %10, 0(%1)
	slt %11, %9, %10
	bnez %11, for_body
	j after_for
for_body:
	lw %12, 0(%13)
	lw %14, 0(%3)
	lw %15, 0(%5)
	add %16, %14, %15
	sw %16, 0(%13)
	lw %17, 0(%3)
	lw %18, 0(%5)
	sw %18, 0(%3)
	lw %19, 0(%5)
	lw %20, 0(%13)
	sw %20, 0(%5)
	j for_incr
for_incr:
	lw %21, 0(%7)
	addi %22, %21, 1
	sw %22, 0(%7)
	j for_condition
after_for:
	lw %23, 0(%13)
	sw %23, 0(zero)
	j after_for
	j after_for
	.size	main, .-main
