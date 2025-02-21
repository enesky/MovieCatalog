#!/bin/bash
# Same file is added to project/.git/hooks/ if you run Clean Project on Android Studio IDE

echo "*************************************************"
echo "*                Running Detekt                 *"
echo "*************************************************"

# Get the current branch name
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

# Finds the last pushed commit in the current branch
LAST_PUSHED_COMMIT=$(git rev-parse origin/$CURRENT_BRANCH)

# Finds the files changed between the last pushed commit and HEAD
CHANGED_FILES=$(git diff --name-only $LAST_PUSHED_COMMIT HEAD)

# An array to store the changed modules
CHANGED_MODULES=()

# Check if there are any changed files between the last pushed commit and HEAD.
# If there are no changed files, don't continue and exit but if there are any changed files, print and continue
if [ -z "$CHANGED_FILES" ]; then
    echo "* No files changed since last push. Skipping     *"
    echo "*************************************************"
    exit 0
else # Print changed files
    echo "*************************************************"
    echo " -> Changed files since last push: "
    echo "$CHANGED_FILES" | sed 's/^/ -> /'
    echo "*************************************************"
fi

# Function to add module to CHANGED_MODULES array if not already present
add_module() {
    local module="$1"
    if [[ -n "$module" && ! " ${CHANGED_MODULES[@]} " =~ " ${module} " ]]; then
        CHANGED_MODULES+=("$module")
    fi
}

# For each changed file, get the module name and add it to the array
while IFS= read -r file; do
    if [[ $file == app/* ]]; then
        add_module "app"
    elif [[ $file == build-logic/* ]]; then
        continue # Ignore build-logic module
    elif [[ $file =~ ^core/([^/]+) ]]; then
        add_module "core:${BASH_REMATCH[1]}"
    elif [[ $file =~ ^feature/([^/]+)/([^/]+) ]]; then
        add_module "feature:${BASH_REMATCH[1]}:${BASH_REMATCH[2]}"
    elif [[ $file =~ ^product/([^/]+) ]]; then
        add_module "product:${BASH_REMATCH[1]}"
    fi
done <<< "$CHANGED_FILES"

# Print changed modules
echo " -> Detekt will run on these modules: "
printf ' --> %s\n' "${CHANGED_MODULES[@]}"
echo "*************************************************"

# Change to the project root directory
while [[ $PWD != / && ! -d .git ]]; do
    cd ..
done

if [[ ! -d .git ]]; then
    echo "Failed to find the project root (.git directory)."
    exit 1
fi

# For each changed module, run detekt
for MODULE in "${CHANGED_MODULES[@]}"; do
    echo "*************************************************"
    echo " -> Running detekt for module $MODULE ..."
    echo "*************************************************"
    if ! ./gradlew "$MODULE:detektAll"; then
        echo "*************************************************"
        echo "*      Detekt failed for module $MODULE         *"
        echo "*  Please fix the issues above before pushing   *"
        echo "*************************************************"
        exit 1
    fi
done

echo "**************************************************"
echo "*    Detekt succeeded for all changed modules    *"
echo "**************************************************"
