Param()

Write-Host "Compiling project..."

# Create output directory
mkdir -Force .\out | Out-Null

# Compile sources (include project root for lib package and src for package sources)
javac -d out -sourcepath ".;src" .\RockPaperScissorsGame.java
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed with exit code $LASTEXITCODE"
    exit $LASTEXITCODE
}

Write-Host "Compilation succeeded. Launching application..."

# Run the app
java -cp out RockPaperScissorsGame
