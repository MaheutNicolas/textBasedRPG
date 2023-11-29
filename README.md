# textBasedRPG
3rd project : The goal is to learned the rope of oriented object programming by 
creating a rpg game with javaFX this time.

## structure 
I try to only use OOP, and the chosen structure is way to complexe.
I restart from scratch 3 times, every time with better understanding of the problem.

### controller 
controller of javaFX help me organise java xml file.

### djevent 
controle the event who happen inside the dungeon.
Event here is not a computer event but a game event.

### fight 
controle fight phase in game

### item
create object item and stock every information needed for the use in game.

### npc 
controle NPC function 

### phase 
controle the position of the player in the game, if the player change place, phase is responsible for that.

### rpg 
start the app, acces database, and stock template object. (one of my many error : this file has acces has to many thing)

### town 
organise instance when player explore town


## Lesson Learned 

I learn some basic in the world of architecture.
But it's clearly not enought.
I code everything like object, but everything can't be object.
I need a fixe structure to help organize the object who will pass trought.
I realize my error, when the inventory pass throught 4 different class in total.

