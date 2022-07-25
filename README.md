# BoltActionOutbreak
Contains: Rules / Controls, Run instructions, compilation, features implemented, separation of work

NOTE: all source code written by us is under the 'gamesrc' directory, with the exception of minor modifications to the dyn4j library

Rules / Controls
	-Before playing the game, you will go through a launcher for tuning to your scrollwheel, it will describe itself.

	-The objective is to get as many points as you can by the end of all game waves.
	-You get points by shooting at zombies on the screen, represented by green circles
		Shots pierce through everything.
    -Every shot, you get points equal to the number of zombies killed squared. e.g. 
        3 zombies in one shot (3)^2 = 9.

	-You can move up/down/left/right with WASD.
	-Neither you nor the zombies can go through grey-colored areas, but your shots can.

	-To fire a shot, hold down LMB to see an aiming line, then release to fire in that direction.
	-You can only fire once , then you need to chamber a new bullet by cycling the bolt.
		You chamber a bullet by unlocking (RMB), opening (MW back), closing (MW forward), and then locking
		(RMB) the bolt.
        
			unlock->open->close->lock
			RMB     MWUP  MWDWN  RMB
            
	-The weapon manipulations can be (accidentally) done out-of-order, or mixed up.
		Look at the graphic depicting weapon state in the top-left to guide yourself!
        
	-You have exactly 5 bullets in your magazine, after 5 shots you will not be able to fire any more.
	-At any point, you can load in more bullets, to a maximum capacity of 5
		To reload bullets, you must unlock and open the bolt.
			
            unlock->open
			RMB	    MWUP
            
		Pressing space repeatedly loads individual bullets into the magazine.
        You do not have to load to capacity. Once loaded, you must close and lock the bolt.
        
			close->lock
			MWDWN  RMB
            
	-Your ammo count is not shown, you must count bullets.
    
	-You have 5 health points, when they reach 0, you die. You only have one life.
	-If you touch a zombie while it is moving, you will take 1 damage, 
     but won't do so again until it makes another move towards you.
	
	-You can see your score in the top-center, weapon status and health in the top-left, and
	 the current wave on the bottom-right.

Run instructions
	-Run the jar file by double clicking.
	-Or run java -cp . .\gamesrc\BAOLauncher.java at the BAO directory.

Compilation
	-Run javac -cp . .\gamesrc\*.java at the BAO directory, everything under framework and dyn4j is part of the
	 imported physics library and so should already be compiled.

Features implemented
	-Please keep in mind that our original feature proposal included as many features as we could think of (rather than 
	 what we though was feasible). We figured that if the external libraries made the job too easy, we could just do more 		 	 
	 features. We specified this in our original project proposal email. We believe our final product is more than
	 enough for the assignment, even if it isn't every single feature we listed.
	
   *-------------------------------------------------------------------------*
   |  Any changes we made to existing features will be enclosed in:    "{}"  |
   |  If every sub-feature was implemented it will be indicated with:  "..." |
   *-------------------------------------------------------------------------*                                                                
                                                                    
	-Top-down 2D view
	-Player WASD to move left/right/down/up.
		...
	-"Zombies" spawn {at random places on the screen} and constantly try to move towards the player.
		...
	-Player health {counter}
		Decremented each time the player is touched by a {moving} zombie.
	-Left-Click on a point in the screen to {aim and} shoot in that direction.
		Shot is a raycast that kills all zombies in its path.
	-Complex bullet loading mechanic
		...
	-Complex bullet-re-loading mechanic
		...
	-Graphics
		GUI elements that show the state of the player.
		Healthbar, ammo, reload state.
	-Sounds
		...

Extra features
	-Launcher for tuning to different mouse wheels.
	-Aiming line on LMB down
		Hidden cursor.
	-Sprite animations for weapon state graphics
		A big chunk was pulled from a cited source, but a lot of modification / adaptation had to be done.
	-Vapor trail.
	-Player hurt if hit by specifically moving zombies, not just when hit.
	-Zombies increase speed ("lunge") when near the player.

Separation of work:
	-Both
		ActionStateHandler.java
		EndScreenGameObject.java
	-Will
		SoundManager.java
		WaveGameObject.java
		WaveHandler.java
		README file
	-Elias
		AnimationManagerGameObject.java
		BAOLauncher.java
		BAOSimulationFrame.java
		GameObject.java
		PlayerGameObject.java
		PlayerHealthGameObject.java
		ScoreBoardGameObject.java
		VaporTrailGameObject.java
		ZombieGameObject.java
	-StackOv
		Animation.java
		Frame.java
		Sprite.java
























































