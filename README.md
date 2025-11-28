# **AutoLink**

### *Connecting car parts, builds, and ideas*

**AutoLink** is an application designed to help car enthusiasts plan and organize car modifications. The user can build by adding parts such as wheels, tires, suspension, and exhaust systems, and then run a fitment check to see if the components are compatible with each other. The checker will verify wheel and tire size, warn of rolling diameter differences, and flag up potential clearance issues. The app will also provide a build summary, parts list, and total build costs from the user’s inputs. This software is intended for hobbyists, students, and auto enthusiasts who want to try out auto modifications offline and in a risk-free environment. It will be useful to those who want to find out how their chosen components interact with each other and function separately before really modifying their vehicles in real life, which can save them time and money. Another use of this app is to just use it as a summary list alongside your journey of modifying a car. It can be a place where you can input all the work you have done in an organized and easy to follow format. It can be used by users to refine their skills and to test out new ways to modify their cars that they might have otherwise missed. 

Why have I chosen to do this specific project? I have always been a car enthusiast. From watching F&F movies when I was younger to now being in a community of like-minded people, this interest of mine came to mind when thinking of project Ideas. I always wanted to integrate my two passions (software development and cars) in a way where I could demonstrate my knowledge and experience. This project is manageable and innovative as it allows me to take two things that I enjoy and put them together into a worthwhile experience. Through **AutoLink** I will be able to apply and implement fundamental software design principles like problem-solving, usability, and modularity, while at the same time doing something that I have an interest in on a personal level.

## *Key Features and Benefits*

- Add parts such as wheels, tires, suspension, and exhaust to create a build  
- Run a fitment check to verify compatibility of components  
- Receive warnings for rolling diameter changes and clearance issues  
- View a complete build summary with part details and total cost  
- Keep an organized record of your modification journey 
 
 ## User Stories:

- As a user, I want to add multiple parts to my build (each with its specifications) so that I can plan my car modifications

- As a user, I want to view the list of parts in my build so that I can review what I’ve added

- As a user, I want to remove or replace a part in my build so that I can update my configuration easily

- As a user, I want to run a fitment check on my build so that I know if my wheels and tire specs are compatible

- As a user, I want to see a summary of my build (including the total cost and any warnings) so that I can evaluate the overall plan

- As a user, I want to have the reminder and option to save my current build and its parts to a file everytime I select the quit option from my application main menu

- As a user, when I start the application I want to be given the option to load previous build and its parts from file

## Instructions for End User

- You can view the panel that displays the parts that have already been added to the build by running the GUI version of AutoLink and looking at the main window. In the center of the window there is a panel labelled **"Parts in Current Build"** that shows a list of all parts currently stored in the active build, including their name, category, and cost.

- You can generate the first required action related to the user story "adding multiple parts to a build" by clicking the **"Add Part"** button at the top of the main window. This opens an input form where you choose a category (wheel, tire, suspension, exhaust, engine, transmission, bumper, sideskirts, diffuser, spoiler, or lights) and enter the details (such as name, cost, and other specs). When you click **"OK"**, the new part is created, added to the build's inventory, and the **"Parts in Current Build"** list is updated.

- You can generate the second required action related to the user story "adding multiple parts to a build" by using the **category filter** located above the parts list. Select a category (for example, "Wheel" or "Tire") from the drop-down box and click the **"Filter"** button to display only the parts of that category in the **"Parts in Current Build"** panel. To see all parts again, choose **"All"** in the drop-down and click **"Filter"** once more.

- You can locate my visual component by looking at the top of the main AutoLink window. There is an image displayed there that serves as the AutoLink logo. This logo image is always visible while the GUI is open and provides a simple visual element in addition to the text-based lists and buttons.

- You can save the state of my application by using the menu bar at the top of the GUI window. Click **"File" → "Save"** to write your current build and its parts inventory to the JSON file used by AutoLink. After saving, you can close the application and later reload your work from the same file.

- You can reload the state of my application by using the menu bar at the top of the GUI window. Click **"File" → "Load"** to read the previously saved build and parts inventory from the JSON file. The **"Parts in Current Build"** panel will update to show the loaded parts, and your active build state will be restored in the GUI.


## Phase 4: Task 2

Fri Nov 28 10:57:34 PST 2025
Added part 'VGL SideSkirts' (SideSkirts) to inventory
Fri Nov 28 10:58:07 PST 2025
Added part 'NA 54' (Engine) to inventory
Fri Nov 28 10:58:11 PST 2025
Set active sideSkirts to 'VGL SideSkirts'
Fri Nov 28 10:58:13 PST 2025
Set active engine to 'NA 54'
Fri Nov 28 10:58:23 PST 2025
Cleared active sideskirts
Fri Nov 28 10:58:26 PST 2025
Cleared active engine

