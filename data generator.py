import random
import string

def random_string(length=5):
    return ''.join(random.choice(string.ascii_letters) for _ in range(length))

def generate_commands(num_commands=1000):
    commands = []
    keys = []

    commands.append("put 0 aaaaa\n")

    key = 1
    for _ in range(num_commands):
        # Randomly choose a command type (weighted towards put)
        cmd_type = random.choices(
            ['put', 'get', 'contains', 'delete'],
            weights=[0.5, 0.2, 0.2, 0.1],
            k=1
        )[0]
        
        
        # For get/contains, use existing keys 80% of the time (if available)
        if cmd_type != 'put' and keys and random.random() < 0.8:
            key = random.choice(keys)
    
        if key != 0:
            insert = random.choices([key, 0, key//2], weights=[0.7, 0.1, 0.2], k=1)[0]

        if cmd_type == 'put':
            value = random_string()
            commands.append(f"put {insert} {value}\n")
            if insert not in keys:
                keys.append(insert)
            if insert == key:
                key += 1
        else:
            commands.append(f"{cmd_type} {insert}\n")
    
    return commands

# Generate commands
commands = generate_commands(100_000)

with open("C:\\Users\\Moham\\Downloads\\python\\testdataLL.txt", 'w') as output:
    for cmd in commands:
        output.write(cmd)
