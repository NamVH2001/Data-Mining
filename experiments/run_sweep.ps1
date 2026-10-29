# Sweep minsup for TCSPM and TCSPM+ on one dataset; appends CSV lines to results.csv
# Usage: .\run_sweep.ps1 -Dataset e_shop_sorted -Query "28,212;280" -Minsups 3500,3800 [-Algos 1,2] [-TimeoutSec 1800]
param(
    [Parameter(Mandatory)] [string] $Dataset,
    [Parameter(Mandatory)] [string] $Query,
    [Parameter(Mandatory)] [int[]] $Minsups,
    [int[]] $Algos = @(1, 2),
    [int] $TimeoutSec = 1800
)
$root = Split-Path $PSScriptRoot -Parent
$build = Join-Path $env:TEMP "tcspm_build"
$csv = Join-Path $PSScriptRoot "results.csv"

$src = @("AlgoTCSPM11", "AlgoTCSPM22", "Itemset", "Sequence", "PseudoSequence", "MemoryLogger", "RunPaperExperiment") |
    ForEach-Object { Join-Path $root "$_.java" }
javac -encoding UTF-8 -d $build @src
if ($LASTEXITCODE -ne 0) { throw "javac failed" }
if (-not (Test-Path $csv)) { "algo,dataset,query,minsup,candidates,patterns,timeSec,maxMemMB" | Set-Content $csv -Encoding UTF8 }

foreach ($m in $Minsups) {
    foreach ($a in $Algos) {
        $out = Join-Path $env:TEMP "tcspm_run_$([guid]::NewGuid()).txt"
        $p = Start-Process java -ArgumentList "-Xmx10g", "-cp", "`"$build`"", "RunPaperExperiment", $a,
            "`"$(Join-Path $root "data\$Dataset.txt")`"", $m, "`"$Query`"" `
            -NoNewWindow -PassThru -RedirectStandardOutput $out
        if (-not $p.WaitForExit($TimeoutSec * 1000)) {
            $p.Kill()
            $line = "{0},{1}.txt,{2},{3},TIMEOUT,,>{4},"  -f @("TCSPM", "TCSPM+")[$a - 1], $Dataset, $Query.Replace(',', ' '), $m, $TimeoutSec
        } else {
            $line = (Get-Content $out | Select-Object -Last 1)
        }
        Remove-Item $out -ErrorAction SilentlyContinue
        Add-Content -Path $csv -Value $line -Encoding UTF8
        $line
    }
}
