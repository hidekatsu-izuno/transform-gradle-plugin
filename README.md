# transform-gradle-plugin

## usage

### build.gradle

```gradle:
plugins {
    id 'net.arnx.transform' version '0.1.1'
}

...

transform {
    from 'data/input'
    into 'data/output'
    rename(/(.*)\.xlsx/, '$1.txt')

    process { src, dest ->
        println(dest)

        def excel = src.toExcel()
        def sheet = excel.getWorksheet("Sheet1")
        dest.withWriter('UTF-8') { out ->
            out.writeLine('' + src.relativePath)
            out.writeLine('' + sheet.firstRowIndex)
            out.writeLine('' + sheet.lastRowIndex)
            out.writeLine(sheet.getCell(0, 0).text)
            out.writeLine(sheet.getCell(0, 1).text)
            out.writeLine(sheet.getCell(1, 0).text)
            out.writeLine(sheet.getCell(1, 1).text)
        }
    }
}
```
