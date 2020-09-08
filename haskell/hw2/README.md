# hw2-FriendlyEvil

Example 1:
```
friendlyevil@friendlyevil:~/tmp$ ./hw2 
program need one argument
friendlyevil@friendlyevil:~/tmp$ ./hw2 ~/tmp/test
/home/friendlyevil/tmp/test> help
cd <folder> -- перейти в директорию
dir -- показать содержимое текущей директории
ls <folder> -- показать содержимое выбранной директории
create-folder <folder-name> -- создать директорию в текущей
cat <file> -- показать содержимое файла
create-file <file-name> -- создать пустой файл в текущей директории
remove <folder | file> -- удалить выборанную директорию или файл
write-file <file> text -- записать текст в файл
find-file <file-name> --  поиск файла в текущией директории и поддиректориях
file-info <file> -- показать информацию о файле
folder-info <folder> -- показать информацию о директории
cvs-init -- инициализация СКВ в текущей выбранной директории
cvs-add-file <file> -- добавление файла в СКВ
cvs-add-folder <folder> -- добавление папки в СКВ
cvs-update <file> comment -- добавление изменений файла в СКВ
cvs-history <file> -- просмотр истории изменений файла
cvs-cat <file> index -- просмотр конкретной ревизии файла
cvs-merge <file> index1 index2 -- объедиение ревизий. За основу взята ревизия index1
cvs-delete-version <file> index -- удалить заданную версию файла из ревизий
cvs-delete <file> -- удалить файл из СКВ
help --  показать руководство по использованию
exit | q -- завершение работы программы
```


Example 2:
```
friendlyevil@friendlyevil:~/tmp$ ./hw2 ~/tmp/test
/home/friendlyevil/tmp/test> dir
1
2
a
/home/friendlyevil/tmp/test> ls .
1
2
a
/home/friendlyevil/tmp/test> ls a
3
/home/friendlyevil/tmp/test> file-info 1
"/home/friendlyevil/tmp/test/1"
permissions Permissions {readable = True, writable = True, executable = False, searchable = False}
changed time 2020-05-08 20:40:27.985722871 UTC
size 16
/home/friendlyevil/tmp/test> folder-info a
"/home/friendlyevil/tmp/test/a"
count of files 1
directory size 15
permissions Permissions {readable = True, writable = True, executable = False, searchable = True}
/home/friendlyevil/tmp/test> cd a
/home/friendlyevil/tmp/test/a> ls .
3
/home/friendlyevil/tmp/test/a> cat 3
33333333333333

/home/friendlyevil/tmp/test/a> create-file 3
Error: file or folder '3' already exist
/home/friendlyevil/tmp/test/a> create-file 4
/home/friendlyevil/tmp/test/a> cd ..
/home/friendlyevil/tmp/test> folder-info a
"/home/friendlyevil/tmp/test/a"
count of files 2
directory size 15
permissions Permissions {readable = True, writable = True, executable = False, searchable = True}
/home/friendlyevil/tmp/test> cd a
/home/friendlyevil/tmp/test/a> write-file 4 "hello \n\n world"
/home/friendlyevil/tmp/test/a> cat 4
hello 

 world
/home/friendlyevil/tmp/test/a> dir
4
3
/home/friendlyevil/tmp/test/a> cd ..
/home/friendlyevil/tmp/test> folder-info a
"/home/friendlyevil/tmp/test/a"
count of files 2
directory size 29
permissions Permissions {readable = True, writable = True, executable = False, searchable = True}
/home/friendlyevil/tmp/test> dir
1
2
a
/home/friendlyevil/tmp/test> remove 1
Error: unknown command
/home/friendlyevil/tmp/test> delete 1
/home/friendlyevil/tmp/test> ls .
2
a
/home/friendlyevil/tmp/test> cat 1
Error: file '1' doesn't exist
/home/friendlyevil/tmp/test> delete a
/home/friendlyevil/tmp/test> ls .
2
/home/friendlyevil/tmp/test> cd a
Error: folder 'a' doesn't exist
/home/friendlyevil/tmp/test> folder-info a
Error: folder 'a' doesn't exist
/home/friendlyevil/tmp/test> q
```
Example 3:
```
friendlyevil@friendlyevil:~/tmp$ ./hw2 ~/tmp/test
/home/friendlyevil/tmp/test> ls .
2
/home/friendlyevil/tmp/test> cvs-init
/home/friendlyevil/tmp/test> create-folder a
/home/friendlyevil/tmp/test> cd a
/home/friendlyevil/tmp/test/a> cvs-init
Error: cvs alredy init
/home/friendlyevil/tmp/test/a> write-file 1 "111111111\n111111111"
/home/friendlyevil/tmp/test/a> cat 1
111111111
111111111
/home/friendlyevil/tmp/test/a> cvs-add-file 1
/home/friendlyevil/tmp/test/a> cvs-history 1
0 initial
/home/friendlyevil/tmp/test/a> write-file 1 "222222222222222222"
/home/friendlyevil/tmp/test/a> cat 1
222222222222222222
/home/friendlyevil/tmp/test/a> cvs-update 1
Error: cvs-update command must have two argument
/home/friendlyevil/tmp/test/a> cvs-update 1 "comment to changes"
/home/friendlyevil/tmp/test/a> cvs-history 1
0 initial
1 comment to changes
/home/friendlyevil/tmp/test/a> cvs-cat 1 0
111111111
111111111
/home/friendlyevil/tmp/test/a> cvs-cat 1
Error: cvs-cat command must have two argument
/home/friendlyevil/tmp/test/a> cvs-cat 1 1
222222222222222222
/home/friendlyevil/tmp/test/a> cvs-cat 1 2
Error: index out of range
/home/friendlyevil/tmp/test/a> cvs-delete-version 1 0
/home/friendlyevil/tmp/test/a> cvs-history 1
0 comment to changes
/home/friendlyevil/tmp/test/a> cvs-cat 1 0
222222222222222222
/home/friendlyevil/tmp/test/a> cvs-delete 1
/home/friendlyevil/tmp/test/a> cvs-history 1
/home/friendlyevil/tmp/test/a> q
```
Exmaple 4:
```
friendlyevil@friendlyevil:~/tmp$ chmod 0 test
friendlyevil@friendlyevil:~/tmp$ ./hw2 ~/tmp/test
/home/friendlyevil/tmp/test: getDirectoryContents:openDirStream: permission denied (Permission denied)
friendlyevil@friendlyevil:~/tmp$ chmod 555 test/
friendlyevil@friendlyevil:~/tmp$ ./hw2 ~/tmp/test
/home/friendlyevil/tmp/test> ls .
2
a
/home/friendlyevil/tmp/test> create-file 1
/home/friendlyevil/tmp/test> cat 1

/home/friendlyevil/tmp/test> write-file 1 asldglksajdg      
/home/friendlyevil/tmp/test> cat 1
asldglksajdg
/home/friendlyevil/tmp/test> find-file 1
/home/friendlyevil/tmp/test/1
/home/friendlyevil/tmp/test/a/1
/home/friendlyevil/tmp/test> cd a
/home/friendlyevil/tmp/test/a> find-file 1
/home/friendlyevil/tmp/test/a/1
/home/friendlyevil/tmp/test/a> q
/home/friendlyevil/tmp/test/1: openFile: permission denied (Permission denied)
```