.info
    offset 0

    start _main

.data
    aNumber 8
    aWord "Hello"
    some_space [10]

.text

_main:
    val r1, aNumber
    ld r0, r1
    mov r6, r0
    val r2, find_and_replace
    add r6, r2
    call print_hello
    xor r6, r6
    ret


print_hello:
    push bp
    mov bp, sp

    val r0, aNumber
    val r1, output_location
    svl r4, 1
    val r5, 0
    head:
        ld r3, r0
        cmp r3, r5
        jeq end
            val r4, 1
        end:
            str r1, r3
            incr r0, 1
            incr r1, 1
    loop r4, head

    mov sp, bp
    pop bp
    ret





