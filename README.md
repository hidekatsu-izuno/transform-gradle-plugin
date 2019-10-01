# transform-gradle-plugin

## usage

### build.gradle

```gradle:
plugins {
    id 'net.arnx.transform-sync' version '0.1.0'
}

transformSync {
    from 'data/input'
    into 'data/output'
    rename(/(.*)\.xlsx/, '$1.txt')

    process { details ->
        println("${details.sourcePath} -> ${details.path}")

        def excel = details.toExcel()
        def sheet = excel.getWorksheet("Sheet1")
        dest.file.withWriter('UTF-8') { out ->
            out.writeLine("${sheet.firstRowIndex}")
            out.writeLine("${sheet.lastRowIndex}")
            out.writeLine(sheet.getCell(0, 0).text)
            out.writeLine(sheet.getCell(0, 1).text)
            out.writeLine(sheet.getCell(1, 0).text)
            out.writeLine(sheet.getCell(1, 1).text)
        }
    }
}
```
