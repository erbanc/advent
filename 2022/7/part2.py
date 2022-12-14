class Directory:
    def __init__(self, child_directories, size, name, file_names, parent_directory):
        self.child_directories = child_directories
        self.parent_directory = parent_directory
        self.size = size
        self.name = name
        self.file_names = file_names

    def add_file_size(self, size_file):
        self.size += int(size_file)
        if self.parent_directory is not None:
            self.parent_directory.add_file_size(size_file)


def find_dir_to_delete(dir, size, size_to_free, min_size_can_free):
    if size_to_free <= dir.size <= min_size_can_free:
        min_size_can_free = dir.size
    for child_dir in dir.child_directories:
        min_size_can_free = find_dir_to_delete(child_dir, size, size_to_free, min_size_can_free)
    if dir.name == '/':
        print("The size of the smallest dir sufficient for the update is " + min_size_can_free.__str__())
    return min_size_can_free


with open('./input.txt', 'r') as f:
    mainDir = Directory([], 0, "/", [], None)
    lines = f.read().strip().split('\n')
    current_directory = mainDir
    for line in lines:
        if line.startswith('$'):
            # command case
            if line.strip('$ ').startswith('cd'):
                # change directory
                destination = line.strip('$ cd ')
                if destination == '..':
                    current_directory = current_directory.parent_directory
                elif destination == '/':
                    current_directory = mainDir
                else:
                    directory_found = 0
                    for directory in current_directory.child_directories:
                        if directory.name == destination:
                            current_directory.child_directories += directory
                            current_directory = directory
                            directory_found = 1
                    if directory_found == 0:
                        new_directory = Directory([], 0, destination, [], current_directory)
                        current_directory.child_directories.append(new_directory)
                        current_directory = new_directory
        elif line.startswith('dir '):
            directory_exists = False
            directory_name = line.strip('dir ')
            for directory in current_directory.child_directories:
                if directory.name == directory_name:
                    directory_exists = True
            if not directory_exists:
                new_directory = Directory([], 0, directory_name, [], current_directory)
        else:
            size_filename = line.split()
            size = size_filename[0]
            filename = size_filename[1]
            file_exists = False
            for name in current_directory.file_names:
                if name == filename:
                    file_exists = True
            if not file_exists:
                current_directory.file_names += filename
                current_directory.add_file_size(size)

    total_sizes_over_100M = 0
    total_used_space = mainDir.size
    total_update_size = 30000000
    total_filesystem_size = 70000000
    total_unused_space = total_filesystem_size - total_used_space
    if total_update_size <= total_unused_space:
        print('Hmmm, apparently there is no issue here, unused space is ' + total_unused_space.__str__())
    total_size_to_free = total_update_size - total_unused_space
    find_dir_to_delete(mainDir, 100000, total_size_to_free, mainDir.size)
