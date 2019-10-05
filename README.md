# transform-sync-gradle-plugin

## usage

### build.gradle

```gradle:
plugins {
    id 'net.arnx.transform-sync' version '0.1.1'
}

transformSync {
    from 'data/input'
    into 'data/output'
    rename /(.*)\.xlsx/, '$1.txt'

    // for a copy only
    preserve {
        include '**'
    }

    processFile { details ->
        println("${details.sourcePath} -> ${details.path}")

        def excel = details.toExcel()
        def sheet = excel.getWorksheet("Sheet1")
        details.file.withWriter('UTF-8') { out ->
            out.writeLine("${sheet.firstRowIndex}")
            out.writeLine("${sheet.lastRowIndex}")
            out.writeLine(sheet.getCell(1, 1).text)
            out.writeLine(sheet.getCell(1, 2).text)
            out.writeLine(sheet.getCell(2, 1).text)
            out.writeLine(sheet.getCell(2, 2).text)
        }
    }
}
```
