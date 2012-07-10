"""
__Interface.py_____________________________________________________

Automatically generated AToM3 syntactic object (DO NOT MODIFY DIRECTLY)
Author: pkilgo
Modified: Sun Apr 15 13:18:31 2012
___________________________________________________________________
"""
from ASGNode import *

from ATOM3Type import *

from ATOM3String import *
from ATOM3List import *
from ATOM3Enum import *
from graph_Interface import *
class Interface(ASGNode, ATOM3Type):

   def __init__(self, parent = None):
      ASGNode.__init__(self)
      ATOM3Type.__init__(self)
      self.graphClass_ = graph_Interface
      self.isGraphObjectVisual = True
      if(hasattr(self, '_setHierarchicalLink')):
        self._setHierarchicalLink(False)
      if(hasattr(self, '_setHierarchicalNode')):
        self._setHierarchicalNode(False)
      self.parent = parent
      self.name=ATOM3String('', 20)
      self.keyword_= self.name
      self.parameters=ATOM3List([ 1, 1, 1, 0],ATOM3String)
      lcobj0=[]
      self.parameters.setValue(lcobj0)
      self.type=ATOM3Enum(['adhoc', 'periodic'], 0, 0)
      self.generatedAttributes = {'name': ('ATOM3String', ),
                                  'parameters': ('ATOM3List', 'ATOM3String'),
                                  'type': ('ATOM3Enum', )      }
      self.realOrder = ['name','parameters','type']
      self.directEditing = [1,1,1]
   def clone(self):
      cloneObject = Interface( self.parent )
      for atr in self.realOrder:
         cloneObject.setAttrValue(atr, self.getAttrValue(atr).clone() )
      cloneObject.keyword_ = cloneObject.name
      ASGNode.cloneActions(self, cloneObject)

      return cloneObject
   def copy(self, other):
      ATOM3Type.copy(self, other)
      for atr in self.realOrder:
         self.setAttrValue(atr, other.getAttrValue(atr) )
      self.keyword_ = self.name
      ASGNode.copy(self, other)

   def preCondition (self, actionID, * params):
      if self.graphObject_:
         return self.graphObject_.preCondition(actionID, params)
      else: return None
   def postCondition (self, actionID, * params):
      if self.graphObject_:
         return self.graphObject_.postCondition(actionID, params)
      else: return None
   def preAction (self, actionID, * params):
      if self.graphObject_:
         return self.graphObject_.preAction(actionID, params)
      else: return None
   def postAction (self, actionID, * params):
      if self.graphObject_:
         return self.graphObject_.postAction(actionID, params)
      else: return None
   def QOCA(self, params):
      """
      QOCA Constraint Template
      NOTE: DO NOT select a POST/PRE action trigger
      Constraints will be added/removed in a logical manner by other mechanisms.
      """
      return # <---- Remove this to use QOCA
      
      """ Get the high level constraint helper and solver """
      from Qoca.atom3constraints.OffsetConstraints import OffsetConstraints
      oc = OffsetConstraints(self.parent.qocaSolver)  
      
      """
      Example constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py
      For more types of constraints
      """
      oc.fixedWidth(self.graphObject_, self.graphObject_.sizeX)
      oc.fixedHeight(self.graphObject_, self.graphObject_.sizeY)
      
      



