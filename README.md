# git-project-olivia

1. I coded stage(). It works
To stage a file, make sure the file exists and then call workingRepository.stage(fileName);. This will add it to the index.

2. I coded commit(String author, String message). It works
Calling workingRepository.commit(authorName, messageContent) will update the HEAD file as well as create an instance where every staged file is saved. This also clears the index, as every unchanged file is now saved.

3. I coded checkout(). It works
Calling workingReposity.checkout(previousCommitHash) will update the HEAD to the previousCommitHash and update the current instance of work to the given commit.

4. I fixed a lot of bugs. One bug I have not fixed is that when you stage a file, if it's inside a directory, all of the files inside that directory will be staged instead of just the staged one.

Example of a situation where this bug would arise:

Initial working repository:
blob ds3r0 dir1/file1.txt
blob qrwio dir1/file2.txt
tree 3r9q8 dir1
blob 4309r file3.txt

After changing file1 and file2's contents but only staging file2, the commited tree would look like:
blob 08rwu dir1/file1.txt
blob 04uwr dir1/file2.txt
tree 9mim9 dir1
blob 4309r file3.txt

Instead of:
blob ds3r0 dir1/file1.txt
blob 04uwr dir1/file2.txt
tree 09ujm dir1
blob 4309r file3.txt

//all of these hashes are completely made up of keyboard smash