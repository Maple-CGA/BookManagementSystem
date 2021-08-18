# Book management system
## Application
#### Video Demo: <https://youtu.be/N3GfUS-J2uE>
#### Description:
This application aids in the management of books in a library, recording and tracking the borrowing and returning of books.
The book lender needs to assign a number or id to the book and the user.
The reason I developed this application is because I thought it would be useful for my mothers' work.

#### Development environment
- Windows 10 Pro
- Eclipse Java EE IDE for Web Developers (Version: Oxygen.3a Release)
- Java SE Development Kit 11.0.12

#### Execution Environment
1. for exe
<br>Windows 8 or 10

2. jar or class
<br>Computer with Java Runtime Environment 1.8 or higher

#### Restrictions
- Book Number
  - The book number must consist of one arbitrary alphabetic character and seven digits.
   (Example: A1234567, F9204068, Z0000000, etc.)
  - The book number can have a meaning such as Check Digit or Checksum, but
   It is not possible to validate the input value from the application side.
   (It does validate that the value consists of one alphabetic character and seven digits)

- User Number
  - The user number must consist of 5 or 8 digits.
  - The user number can have a meaning such as Check Digit or Checksum, but the application cannot validate the input value.
   (The application will verify that the number is composed of 5 or 8 digits.)

- Others
<br>Since the purpose of this application is to assist in book management, it cannot record information such as book titles.
The same applies to the user, who cannot have any information other than the number. It is necessary for the application user to link each number to the data.
It is necessary for the application user to link each number to the data.

#### How to use
- Lending
  1. Press the "Borrow" button at the top of the screen, or press the F7 key on the keyboard.
  2. Enter the borrower's number in the "User Number" field.
  3. Enter the number of the book you want to borrow in the "Reference Number" field.
  4. Press the Enter key or click the Execute button at the bottom of the screen to execute the checkout.

- Returning Books
  1. Press the Return button at the top of the screen or press the F8 key on your keyboard.
  2. Enter the number of the book you want to return in the Material Number field.
  3. Press the Enter key or the Execute button at the bottom of the screen to execute the loan.

- Search
  1. Press the Search button at the top of the screen or press the F6 key on your keyboard.
  2. Specify the user number, material number, and other search conditions.
  3. Press the Search button to execute.
  4. If necessary, you can export the search results to csv from the Save button.

## Distribution form
This software is freeware. You can use it free of charge, but it is prohibited to copy, modify, or redistribute it without permission.

## Copyright.
**©2021 Maple32768**

## Disclaimer
Maple32768 assumes no responsibility whatsoever for any problems or troubles that may arise from the use of this software.
Maple32768 assumes no responsibility for any problems or troubles arising from the use of this software.
Please use this software at your own risk.

## Terms of Use
- The internal image materials are my own work. Diversion is prohibited.
- Secondary distribution of the files contained in this software is prohibited.
- Use of the software for purposes that are offensive to public order and morals is prohibited. Please use this software within the scope of common sense.

## Update History
- 2021/08/18 - ver 1.1
  - GUI adjustment
  - Improved database.
  - Added 2 new setting items

- 2021/08/15 - ver 1.0
  - Implementation of basic system
  - Implementation of GUI

## Created by.
[Maple32768](https://twitter.com/maple_osg)

## Contact.
If you have any bugs or questions, please contact me at [Maple32768](https://twitter.com/maple_osg) DM. However, unauthorized reproduction or quotation of the transmitted contents is strictly prohibited.

## Translate
Translated with www.DeepL.com/Translator (free version)
