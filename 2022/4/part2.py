with open('./input.txt', 'r') as f:
    elf_pairs = f.read().strip().split('\n')
    pairs_overlapping = 0
    for elf_pair in elf_pairs:
        first_range = elf_pair.split(',')[0]
        second_range = elf_pair.split(',')[1]
        inf_first_pair = int(first_range.split('-')[0])
        sup_first_pair = int(first_range.split('-')[1])
        inf_second_pair = int(second_range.split('-')[0])
        sup_second_pair = int(second_range.split('-')[1])
        if (inf_first_pair <= inf_second_pair <= sup_first_pair) or (
                inf_first_pair <= sup_second_pair <= sup_first_pair) or (
                inf_second_pair <= inf_first_pair <= sup_second_pair) or (
                inf_second_pair <= sup_first_pair <= sup_second_pair):
            pairs_overlapping += 1

print(pairs_overlapping)
