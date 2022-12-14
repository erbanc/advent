with open('./input.txt', 'r') as f:
    calories = f.read().split('\n')
    top_calories = 0
    current_calories = 0
    for calorie in calories:
        if calorie == '':
            if current_calories > top_calories:
                top_calories = current_calories
            current_calories = 0
        else:
            current_calories += int(calorie)
print(top_calories)
