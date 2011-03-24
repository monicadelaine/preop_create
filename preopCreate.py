
#======TODO=========================
#  Wrapping Bluetooth module
#
#===================================

#======GENERAL INFO=================
#  This is a wrapper for the pycreate library to make it have PREOP-like calls.
#  For more info on PREOP, please see cs.ua.edu/preop
#
#======SYSTEM REQUIREMENTS========== 
#  Written for Python 3.1, though it should work on all versions of Python.
#  In order for this to work, YOU MUST HAVE THE PYCREATE LIBRARY INSTALLED.
#
#===================================

from create import *

#======GLOBAL VARS==================
FORWARD = 1
BACKWARD = -1
LEFT = 1
RIGHT = -1

SAND = 0
DIRT = 1

C = 0
C_SHARP = 1
D = 2
D_SHARP = 3
E = 4
F = 5
F_SHARP = 6
G = 7
G_SHARP = 8
A = 9
A_SHARP = 10
B = 11

DEFAULT_SPEED = 25 #in cm/sec


#======NOTE & SONG CLASSES==========
class note:
	"""This class creates a note. It stores the following information:
	Note: C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B
	Octave: 2-8
	Beats per minute
	Length of note (in beats)
	Plus (in beats)
	"""
	def __init__(self, note, octave, bpm = 120, length = 1, plus = 0):
		self._note = (octave+1)*12 + note #conversion to midi number using ISO standard
		self._bpm = bpm
		self._length = length + plus
		
class song:
	"""This class creates a song. It stores a list of notes along with the song number. """
	def __init__(self):
		self.songNum = 0
		self.notelist = []
		
	def addNote(self, note):
		"""Usage: song.addNote(note). Please use included note class to create a note. """
		self.notelist.append(note)

#======WORLD CLASS==================
#Note: if we want to implement some the other functionality provided by ALICE, we would probably need to add it here.
#For now, this just functions as a container to help keep track of multiple robots.
class world:
	def __init__(self, t):
		self._type = t
		self.items = {} #uses numerical keys to keep track of objects. Each object is responsible for its own key	
		
	def __findKey__(self):
		t = 0
		while(self.items.has_key(t)):
			t = t+1
		return t
		
	def addRobot(self, r):
		""" Use this method to add a robot to your world """
		t = self.__findKey__()
		self.items[t] = r
		r._key = t #any object added to the world must have this attribute
		
	def removeRobot(self, r):
		""" Use this method to remove a robot from the world """
		self.items.pop(r._key)
		r._robot.shutdown()
		

#==========ROBOT==============
#Note: Only describes an iRobot Create.
class robot_create:
	
	#MAJOR TODO: WRAPPING BLUETOOTH MODULE IN connectedTo and the constructor
	#def connectedTo(self):
	def __init__(self, p): # the only thing that is different from preop is that creating a robot requires specifying a port number. I didn't see any way around this one.
		""" You must specify the port number used to connect to the robot to create it. """
		self._port = p
		_key = -1  # -1 indicates that it has not been assigned to the world
		self._robot = Create(self._port)
		_LEDColor = 255 #Ranges in 0-255. 0=Green, 255=Red
		_PlayLED = 0
		_AdvanceLED = 0
		
	def _reconnect(self):
		""" Restarts connection to robot. Useful if sensors have not properly initialized. """
		self._robot.reconnect(self._port)
		
	def move(self, dir, amount, duration=1):
		"""Usage: robot.move(direction, amount, duration). Direction may be FORWARD or BACKWARD. Specify amount in meters, duration in seconds. If no duration is specified, default value is 1. """
		amount = amount*100 #convert from meters to centimeters
		self._robot.driveDirect(dir*amount/duration, m*amount/duration)
		self._robot.waitDistance(amount)
		self._robot.stop()
		
	def turn(self, dir, amount, duration=1):
		"""Usage: robot.turn(direction, amount, duration). Direction may be LEFT or RIGHT. Amount is specified in revolutions. Duration is specified in seconds. If no duration is specified, default value is 1. """
		#direction is interpreted as relative to robot, i.e. left as CCW, right as CW
		self._robot.go(1, dir*amount*360/duration) 
		self._robot.waitAngle(amount*360)
		self._robot.stop()		
	
	def moveAtSpeed(self, dir, speed, dur =1):
		"""Usage: robot.moveAtSpeed(direction, speed, duration). Direction may be FORWARD or BACKWARD. Speed is in m/sec, and duration is in seconds. If no duration is specified, default value is 1. """
		self._robot.driveDirect(dir*speed, dir*speed)
		self._robot.waitDistance(speed*duration*100)
		self._robot.stop()
	
	def turnAtSpeed(self, dir, speed, dur=1):
		"""Usage: robot.turnAtSpeed(direction, speed, duration). Direction may be LEFT or RIGHT. Speed is in revolutions per second, and duration is in seconds. If no duration is specified, default value is 1. """
		self._robot.go(1, dir*speed*360)
		self._robot.waitAngle(speed*dur*360)
		self._robot.stop()
		
	def playNote(self, n):
		"""Usage: robot.playNote(note). Please use included note class to create a note. """
		self._robot.playNote(n.note, n.length, 0)
	
	def playSong(self, s):
		"""Usage: robot.playSong(song). Please use included song class to create a song. """
		self._robot.setSong(s.songNum, s.noteList)
		self._robot.playSongNumber(s.songNum)
			
	def isAdvanceLEDOn(self, val):
		"""Usage: robot.isAdvanceLEDOn(boolean). The boolean value should be True or False, depending on whether you want the Advance LED on or off. """
		if (val == True):
			self._AdvanceLED = 1
		else:
			self._AdvanceLED = 0
		setLEDs(self._LEDColor, 255, self._PlayLED, self._AdvanceLED)
	
	def powerLEDColor(self, color):
		"""Usage: robot.powerLEDColor(int). The int value entered should range between 0-255. 0 = Green, 255 = Red. """
		if(color>0 and color<255):
			self._LEDColor = color
	
	def isPlayLEDOn(self, val):
		"""Usage: robot.isPlayLEDOn(boolean). The boolean value should be True or False, depending on whether you want the Play LED on or off. """
		if (val == True):
			self._PlayLED = 1
		else:
			self._PlayLED = 0
		setLEDs(self._LEDColor, 255, self._PlayLED, self._AdvanceLED)
	
	def leftWheelIsDropped(self):
		val = self._robot.getSensor(BUMPS_AND_WHEEL_DROPS)
		if (val[1] == 1): #dropped = 1, not dropped = 0
			return True
		return False
	
	def rightWheelIsDropped(self):
		val = self._robot.getSensor(BUMPS_AND_WHEEL_DROPS)
		if (val[2] == 1): #dropped = 1, not dropped = 0
			return True
		return False
	
	def casterWheelIsDropped(self):
		val = self._robot.getSensor(BUMPS_AND_WHEEL_DROPS)
		if (val[0] == 1): #dropped = 1, not dropped = 0
			return True
		return False
	
	def rightBumperIsDepressed(self):
		val = self._robot.getSensor(BUMPS_AND_WHEEL_DROPS)
		if (val[4] == 1): #dropped = 1, not dropped = 0
			return True
		return False
	
	def leftBumperIsDepressed(self):
		val = self._robot.getSensor(BUMPS_AND_WHEEL_DROPS)
		if (val[3] == 1): #dropped = 1, not dropped = 0
			return True
		return False
	
	def playButtonIsPressed(self):
		val = self._robot.getSensor(BUTTONS)
		if (val[1] == 1): #button pressed = 1, not pressed = 0
			return True
		return False
		
	def advanceButtonIsPressed(self):
		val = self._robot.getSensor(BUTTONS)
		if (val[0] == 1): #button pressed = 1, not pressed = 0
			return True
		return False

	def detectsWall(self):
		if (self._robot.getSensor(WALL) == 1): #wall = 1, no wall = 0
			return True
		return False
	
	


		
#EOF