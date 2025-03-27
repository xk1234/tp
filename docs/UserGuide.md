---
layout: page
title: User Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Introduction

### Who is AscendNetwork for?

AscendNetwork is built with **network marketeers** in mind—people who often deal with numerous leads, customers, and partners. It is specially designed for those who prefer or are comfortable with typing commands. By centralizing all contact information, you can organise and keep track of important contact details and stats. 

<div markdown="span" class="alert alert-info">
**:information_source: Note:** We assume you have basic familiarity with your computer, such as downloading files and opening folders. Where advanced technical actions are required, we will guide you step-by-step.
</div>

### What can you expect?

1. A **fast** way to input and manage contacts. If you can type quickly, you’ll love the efficiency.
1. A **user-friendly interface** that still leverages simple text commands (just like chatting in a messaging app).

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. **Open up your “command terminal”**.
   * A command terminal is a program where you can type text-based commands to your computer.
   * On **Windows**, look for “Command Prompt” or “PowerShell” in your Start Menu.
   * On **Mac**, open “Terminal” from your Applications > Utilities folder.
   * On **Linux**, open the terminal you have installed.

1. **Check if Java 17 (or above) is installed**.
   * Type into the terminal:
     ```sh
     java -version
     ```
     You should see a string that includes the text 17. For example:
     ```sh
     java version "17"
     Java(TM) SE Runtime Environment (build 17)
     Java HotSpot(TM) 64-Bit Server VM (build 23.2-b04, mixed mode)
     ```
   * If Java is not found, or if the version is not 17, please follow the instruction on Oracle's website for your platform:
     * [Windows](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html#GUID-DAF345BA-B3E7-4CF2-B87A-B6662D691840)
     * [Mac](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-macos.html#GUID-F575EB4A-70D3-4AB4-A20E-DBE95171AB5F)
     * [Linux](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-linux-platforms.html#GUID-4A6BD592-1840-4BB4-A758-4CD49E9EE88B)

     Afterwards, repeat the step above to verify if it is installed.

1. **Download the latest `ascendnetwork.jar` file** from our [GitHub releases page](https://github.com/AY2425S2-CS2103T-T14-4/tp/releases).

   ![Download Jar](images/DownloadJar.png)

1. **Place the `.jar` file** into the folder you want to use as the “home folder” for AscendNetwork, which stores your data.
   * For example, you could create a folder called `AscendNetworkHome` on your Desktop.

1. **Run AscendNetwork**.
   * Still in your command terminal, use the `cd` command to move into that folder. For example:
     ```sh
     cd Desktop/AscendNetworkHome
     ```
   * Then type:
     ```sh
     java -jar addressbook.jar
     ```
   * After a few seconds, the AscendNetwork interface should appear. You will see some sample contacts included by default.

   ![AscendNetwork User Interface](images/Ui.png)

6. **Try out some commands** in the command box.

   ![Insert screenshot of a user typing commands, if available](images/CommandInput.png)

   * **`help`** : Opens the help window.
   * **`list`** : Lists all existing contacts.
   * **`add n/John Doe p/98765432 e/johnd@example.com a/John Street, block 123, #01-01`** : Adds a contact named `John Doe`, whos contact number is `98765432`, has an `email of johnd@example.com` and lives at `John Street, block 123, #01-01`.
   * **`delete 3`** : Deletes the 3rd contact in the current list.
   * **`clear`** : Deletes **all** contacts.
   * **`exit`** : Closes the app.

<div markdown="block" class="alert alert-info">

**:information_source: AscendNetwork commands follow a straightforward format:**

* **UPPER_CASE** words are placeholders (e.g., `NAME` in `add n/NAME`).
* **Square brackets `[ ]`** indicate optional items (e.g., `[t/TAG]`).
* **Items with `...`** can appear multiple times or not at all (e.g., `[t/TAG]...`).
* **Curly braces `{ }` separated by `|`** mean “choose one” (e.g., `{n/NAME | t/TAG}`).
* **Parameters can be in any order.**
* **Extraneous parameters** are ignored for commands that do not accept any (e.g., `help 123` is treated as `help`).

_If you are reading this from a PDF, watch out for spacing issues when copying multi-line commands._
</div>

--------------------------------------------------------------------------------------------------------------------

## Features

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating your contacts: `find`

Finds contacts by matching name and tag keywords you provide.

Format: `find {n/NAME_KEYWORD [NAME_KEYWORD]... [t/TAG_KEYWORD [TAG_KEYWORD]...] | t/TAG_KEYWORD [TAG_KEYWORD]... [n/NAME_KEYWORD [NAME_KEYWORD]...]}`

<div markdown="span" class="alert alert-primary">:bulb: **Tip**: Run this command to limit the contacts that commands acting on displayed ones affect</div>

* `NAME_KEYWORD` is a **single word** searching only name. e.g. In `n/Hans Bo` both `Hans` and `Bo` are keywords
* `TAG_KEYWORD` is a **single word** searching only tags. e.g. In `t/downline customer` both `downline` and `customer` are keywords
* Supply **at least one** of `n/NAME_KEYWORD` or `t/TAG_KEYWORD`.
* When providing only `NAME_KEYWORD`, finds persons whose name contain at least one keyword (i.e. `OR` search). e.g. Providing `n/Hans Bo` finds `Hans Gruber`, `Bo Yang`
* When providing only `TAG_KEYWORD`, finds persons whose tags contain at least one keyword (i.e. `OR` search). e.g. Providing `t/downline customer` finds person with tags `downline` and `family`, as well as person with tags `customer` and `friend`
* When providing both `NAME_KEYWORD` and `TAG_KEYWORD`, finds persons matching both name and tag search (i.e. `AND` search between name and tags). e.g. Providing `n/Hans Bo t/downline customer` finds `Hans Gruber` and with tags `downline` and `family`, but not `Bo Yang` with tags `family` and `friend`
* Searching ignores case. e.g `n/hans` matches `Hans` name, `t/customer` matches `Customer` tag
* The order of the keywords does not matter. e.g. `n/Hans Bo` matches `Bo Hans` name, `t/downline customer` matches `customer` and `downline` tags
* Matches only full words. e.g. `n/Han` does not match `Hans` name, `t/downlines` does not match `downline` tag

Examples:
* `find n/John` finds `john` and `John Doe`
* `find n/alex david t/downline customer` finds `Alex Yeoh` with tag `downline`, `David Li` with tag `customer`<br>
  ![result for 'find n/alex david t/downline customer'](images/findAlexDavidResult.png)

### Tagging multiple contacts at once: `tag`

Adds your specified tag(s) to multiple contacts.

Format: `tag TAG [TAG]…`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:** Ensure you filter to the correct group of contacts before using the command, or it may add tags to contacts unintentionally.</div>

* Display **at least one contact** before running the command.
* `TAG` is a word of **digits or letters (both uppercase and lowercase)**. e.g. `customer1`
* Adds all `Tag` to your currently displayed contacts. e.g. Running `tag customer` when only `Hans` and `Bo` are displayed adds `customer` tag only to them.
* Appends to your contact's existing tags instead of replacing. e.g. Running `tag vip` when a displayed contact with `customer` tag changes their tag to `customer vip`
* Skips contacts who already have all `TAG`.

Examples:
* `tag downline` adds the `downline` tag to all your contacts in the current list.
* `tag customer vip` adds both `customer` and `vip` tags to all your contacts in the current list.

### Removing tags from multiple contacts: `rmtag`

Removes your specified tag(s) from multiple contacts.

Format: `rmtag TAG [TAG]…`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:** Ensure you filter to the correct group of contacts before using the command, or it may remove tags from contacts unintentionally.</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:** You can use this command to manage temporary tags that no longer applies to multiple contacts at the same time. e.g. An event that ended.</div>

* Display **at least one contact** before running the command.
* `TAG` is a word of **digits or letters (both uppercase and lowercase)**. e.g. `customer1`
* Removes all `Tag` from your currently displayed contacts. e.g. Running `rmtag customer` when only `Hans` and `Bo`, that both have `customer`, are displayed tag removes it only from them.
* Skips contacts who do not have any `TAG`.

Examples:
* `rmtag dinner` removes the `dinner` tag from all your contacts in the displayed list.
* `rmtag customer vip` removes both `customer` and `vip` tags from all your contacts in the displayed list.

### Including your contacts by name to specific group: `incl`

Adding contacts with matching name from your contacts to the list.

Format: `incl {n/NAME_KEYWORD [NAME_KEYWORD]...}`

* `NAME_KEYWORD` is same as defined above.
* Supply at least one of `n/NAME_KEYWORD`
* It includes persons whose name contain at least one keyword. e.g. Providing `n/Hans Bo` includes `Hans Gruber`, `Bo Yang`
* The order of the keywords does not matter. e.g. `n/Hans Bo` matches `Bo Hans` name
* Searching ignores case for the name. e.g `n/hans` matches `Hans` name
* Matches only full words e.g. `n/Han` does not match `Hans` name

Examples:
* `incl n/John` finds `john` and `John Doe`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: How can I back up my data?
**A**: You can manually back up your `addressbook.json` file by copying it to a safe location.

**Q**: Can I import contacts from another system?
**A**: Currently, AscendNetwork does not support direct imports from CSV or other contact management systems.

**Q**: Can I export contacts to another system?
**A**: Yes, you can export selected contacts as CSV with the `export` command. Then you can import contacts from CSV to external systems that support imports, such as Google Contacts.

**Q**: Can multiple users access the same address book?
**A**: AscendNetwork is designed for single-user use. If multiple users need to access the same contact database, they must manually share the `addressbook.json` file.

**Q**: Will AscendNetwork work on mobile devices?
**A**: No, AscendNetwork is designed for desktop operating systems such as Windows, macOS, and Linux.

**Q**: Does AscendNetwork require an internet connection?
**A**: No, all data is stored locally on your device, and the app works entirely offline.

**Q**: How many contacts can AscendNetwork keep?
**A**: While there is no hard limit, we advise not having more than 1000 contacts for performance reasons.

**Q**: How do I update AscendNetwork to a newer version?
**A**: Download the latest .jar file from [here](https://github.com/AY2425S2-CS2103T-T14-4/tp/releases) and replace your existing .jar file with the new one. Your data will remain intact if it is stored in the same folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find {n/NAME_KEYWORD [NAME_KEYWORD]... [t/TAG_KEYWORD [TAG_KEYWORD]...] \| t/TAG_KEYWORD [TAG_KEYWORD]... [n/NAME_KEYWORD [NAME_KEYWORD]...]}` <br> e.g., `find n/James Jake t/downline customer`
**Tag** | `tag TAG [TAG]…` e.g. `tag customer vip`
**Remove Tag** | `rmtag TAG [TAG]…`<br> e.g. `rmtag customer vip`
**List** | `list`
**Help** | `help`
