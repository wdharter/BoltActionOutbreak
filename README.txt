Contains: Rules / Controls, Run instructions, compilation, features implemented, separation of work

Rules / Controls
	-Before playing the game, you will go through a launcher for tuning to your scrollwheel, it will describe itself.

	-The objective is to get as many points as you can by the end of all game waves
	-You get points by shooting at zombies on the screen, represented by green circles
		Shots pierce through everything
		1 point per zombie multiplied by the amount of zombies hit by the same shot 
		ex: killing three enemies, 1 point per enemy * 3 = 9 points

	-You can move up/down/left/right with WASD
	-Neither you nor the zombies can go through grey-colored areas, but your shots can

	-To fire a shot, press down on LMB to see an aiming line, then release to fire in that direction.
	-You can only fire once , then you need to chamber a new bullet
		You chamber a bullet by unlocking (RMB), opening (MW back), closing (MW forward), and then locking
		(RMB) the bolt
			unlock->open->close->lock
			RMB     MW U  MW D   RMB
	-The rifle bolt movements can be (accidentally) done out-of-order, or mixed up
		Look at the visual in the top-left to guide yourself!
	-You have exactly 5 bullets in your magazine, after 5 shots you will not be able to fire any more
	-At any point, you can load in more bullets, to a maximum of 5
		To reload bullets, you must
			unlock->open
			RMB	MW U
		Then press spacebar for each bullet you want to reload
		Then
			close->lock
			MW D   RMB
	-Your ammo count is not shown, you must count bullets

	-You have 5 health points, when they reach 0, you lose
	-If you touch a zombie while it is moving, it will deplete 1 health point, but won't do so again until it stops 	 	 
	 moving and starts again.
	
	-You can see your score on the top-center, weapon bolt status on the top-left, health on the top-left, and
	 the current wave on the bottom-right

Run instructions
	-Run the jar file either by double clicking

Compilation

Features implemented
	-Please keep in mind that our original feature proposal included as many features as we could think of (rather than 
	 what we though was feasible), just so if our external libraries made the job too easy, we could just do more 		 	 
	 features. We specified this in our original project proposal email.
	-Any changes we made to existing features will be encased in {}
	-... means every sub-feature was implemented

	-Top-down 2D view
	-Player WASD to move left/right/down/up
		...
	-"Zombies" spawn {at random places on the screen} and constantly try to move towards the player
		...
	-Player health {counter}
		Decremented each time the player is touched by a {moving} zombie
	-Left-Click on a point in the screen to shoot in that direction
		Shot is a raycast that kills all zombies in its path
	-Complex bullet loading mechanic
		...
	-Complex bullet-re-loading mechanic
		...
	-Graphics
		GUI elements that show the state of the player
		Healthbar, ammo, reload state
	-Sounds
		...
Extra features
	-Launcher for tuning to different mouse wheels
	-Aiming line on LMB down
		Hidden cursor
	-Sprite animations for weapon state graphics
		A big chunk was pulled from a cited source, but a lot of modification / adaptation had to be done
	-Vapor trail
	-Player hurt if hit by specifically moving zombies, not just when hit
	-Zombies increase speed ("lunge") when near the player

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




