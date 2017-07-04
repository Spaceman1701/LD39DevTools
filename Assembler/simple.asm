.info
    offset 0

    start _main

.data
    aNumber 6
    some_space [10]

.text
    val r1, 5
    ld r0, r1
    mov r6, r0
    val r2, aNumber
    add r6, r2
    call print_hello
    xor r6, r6
    ret





