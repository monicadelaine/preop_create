"""
__ROSApp_MDL.py_____________________________________________________

Automatically generated AToM3 Model File (Do not modify directly)
Author: pkilgo
Modified: Sun Apr 15 22:01:24 2012
____________________________________________________________________
"""
from stickylink import *
from widthXfillXdecoration import *
from CD_Class3 import *
from CD_Association3 import *
from CD_Inheritance3 import *
from graph_CD_Association3 import *
from graph_CD_Class3 import *
from graph_CD_Inheritance3 import *
from ATOM3Enum import *
from ATOM3String import *
from ATOM3BottomType import *
from ATOM3Constraint import *
from ATOM3Attribute import *
from ATOM3Float import *
from ATOM3List import *
from ATOM3Link import *
from ATOM3Connection import *
from ATOM3Boolean import *
from ATOM3Appearance import *
from ATOM3Text import *
from ATOM3Action import *
from ATOM3Integer import *
from ATOM3Port import *
from ATOM3MSEnum import *

def ROSApp_MDL(self, rootNode, CD_ClassDiagramsV3RootNode=None):

    # --- Generating attributes code for ASG CD_ClassDiagramsV3 ---
    if( CD_ClassDiagramsV3RootNode ): 
        # name
        CD_ClassDiagramsV3RootNode.name.setValue('ROSApp')

        # author
        CD_ClassDiagramsV3RootNode.author.setValue('Paul Kilgo')

        # showCardinalities
        CD_ClassDiagramsV3RootNode.showCardinalities.setValue((None, 1))
        CD_ClassDiagramsV3RootNode.showCardinalities.config = 0

        # showAssociationBox
        CD_ClassDiagramsV3RootNode.showAssociationBox.setValue((None, 1))
        CD_ClassDiagramsV3RootNode.showAssociationBox.config = 0

        # description
        CD_ClassDiagramsV3RootNode.description.setValue('\n')
        CD_ClassDiagramsV3RootNode.description.setHeight(15)

        # showAttributes
        CD_ClassDiagramsV3RootNode.showAttributes.setValue((None, 1))
        CD_ClassDiagramsV3RootNode.showAttributes.config = 0

        # showActions
        CD_ClassDiagramsV3RootNode.showActions.setValue((None, 1))
        CD_ClassDiagramsV3RootNode.showActions.config = 0

        # showConditions
        CD_ClassDiagramsV3RootNode.showConditions.setValue((None, 1))
        CD_ClassDiagramsV3RootNode.showConditions.config = 0

        # attributes
        CD_ClassDiagramsV3RootNode.attributes.setActionFlags([ 1, 1, 1, 0])
        lcobj1 =[]
        cobj1=ATOM3Attribute(self.types)
        cobj1.setValue(('name', 'String', None, ('Key', 1), ('Direct Editing', 1)))
        cobj1.initialValue=ATOM3String('', 20)
        cobj1.isDerivedAttribute = False
        lcobj1.append(cobj1)
        cobj1=ATOM3Attribute(self.types)
        cobj1.setValue(('author', 'String', None, ('Key', 0), ('Direct Editing', 1)))
        cobj1.initialValue=ATOM3String('Annonymous', 20)
        cobj1.isDerivedAttribute = False
        lcobj1.append(cobj1)
        cobj1=ATOM3Attribute(self.types)
        cobj1.setValue(('description', 'Text', None, ('Key', 0), ('Direct Editing', 1)))
        cobj1.initialValue=ATOM3Text('\n', 60,15 )
        cobj1.isDerivedAttribute = False
        lcobj1.append(cobj1)
        CD_ClassDiagramsV3RootNode.attributes.setValue(lcobj1)

        # constraints
        CD_ClassDiagramsV3RootNode.constraints.setActionFlags([ 1, 1, 1, 0])
        lcobj1 =[]
        CD_ClassDiagramsV3RootNode.constraints.setValue(lcobj1)
    # --- ASG attributes over ---


    self.obj26=CD_Class3(self)
    self.obj26.isGraphObjectVisual = True

    if(hasattr(self.obj26, '_setHierarchicalLink')):
      self.obj26._setHierarchicalLink(False)

    # QOCA
    self.obj26.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <---- Remove this to use QOCA\n\n""" Get the high level constraint helper and solver """\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.fixedWidth(self.graphObject_, self.graphObject_.sizeX)\noc.fixedHeight(self.graphObject_, self.graphObject_.sizeY)\n\n'))

    # Graphical_Appearance
    self.obj26.Graphical_Appearance.setValue( ('ROSNode', self.obj26))

    # name
    self.obj26.name.setValue('ROSNode')

    # attributes
    self.obj26.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    cobj2=ATOM3Attribute(self.types)
    cobj2.setValue(('name', 'String', None, ('Key', 1), ('Direct Editing', 1)))
    cobj2.initialValue=ATOM3String('some_node', 20)
    cobj2.isDerivedAttribute = False
    lcobj2.append(cobj2)
    self.obj26.attributes.setValue(lcobj2)

    # Abstract
    self.obj26.Abstract.setValue((None, 0))
    self.obj26.Abstract.config = 0

    # cardinality
    self.obj26.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSPublish', (('Source', 'Destination'), 0), '0', 'N'))
    lcobj2.append(cobj2)
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSSubscribe', (('Source', 'Destination'), 1), '0', 'N'))
    lcobj2.append(cobj2)
    self.obj26.cardinality.setValue(lcobj2)

    # display
    self.obj26.display.setValue('Attributes:\n  - name :: String\nMultiplicities:\n  - To ROSPublish: 0 to N\n  - From ROSSubscribe: 0 to N\n')
    self.obj26.display.setHeight(15)

    # Actions
    self.obj26.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj26.Actions.setValue(lcobj2)

    # Constraints
    self.obj26.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj26.Constraints.setValue(lcobj2)

    self.obj26.graphClass_= graph_CD_Class3
    if self.genGraphics:
       new_obj = graph_CD_Class3(120.0,40.0,self.obj26)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Class3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.17578125, 1.0]
    else: new_obj = None
    self.obj26.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj26)
    self.globalAndLocalPostcondition(self.obj26, rootNode)
    self.obj26.postAction( rootNode.CREATE )

    self.obj27=CD_Class3(self)
    self.obj27.isGraphObjectVisual = True

    if(hasattr(self.obj27, '_setHierarchicalLink')):
      self.obj27._setHierarchicalLink(False)

    # QOCA
    self.obj27.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <---- Remove this to use QOCA\n\n""" Get the high level constraint helper and solver """\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.fixedWidth(self.graphObject_, self.graphObject_.sizeX)\noc.fixedHeight(self.graphObject_, self.graphObject_.sizeY)\n\n'))

    # Graphical_Appearance
    self.obj27.Graphical_Appearance.setValue( ('ROSTopic', self.obj27))

    # name
    self.obj27.name.setValue('ROSTopic')

    # attributes
    self.obj27.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    cobj2=ATOM3Attribute(self.types)
    cobj2.setValue(('name', 'String', None, ('Key', 1), ('Direct Editing', 1)))
    cobj2.initialValue=ATOM3String('some_topic', 20)
    cobj2.isDerivedAttribute = False
    lcobj2.append(cobj2)
    self.obj27.attributes.setValue(lcobj2)

    # Abstract
    self.obj27.Abstract.setValue((None, 0))
    self.obj27.Abstract.config = 0

    # cardinality
    self.obj27.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSPublish', (('Source', 'Destination'), 1), '0', 'N'))
    lcobj2.append(cobj2)
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSSubscribe', (('Source', 'Destination'), 0), '0', 'N'))
    lcobj2.append(cobj2)
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSType', (('Source', 'Destination'), 0), '0', '1'))
    lcobj2.append(cobj2)
    self.obj27.cardinality.setValue(lcobj2)

    # display
    self.obj27.display.setValue('Attributes:\n  - name :: String\nMultiplicities:\n  - From ROSPublish: 0 to N\n  - To ROSSubscribe: 0 to N\n  - To ROSType: 0 to 1\n')
    self.obj27.display.setHeight(15)

    # Actions
    self.obj27.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj27.Actions.setValue(lcobj2)

    # Constraints
    self.obj27.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj27.Constraints.setValue(lcobj2)

    self.obj27.graphClass_= graph_CD_Class3
    if self.genGraphics:
       new_obj = graph_CD_Class3(420.0,200.0,self.obj27)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Class3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.07734375, 1.0844262295081968]
    else: new_obj = None
    self.obj27.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj27)
    self.globalAndLocalPostcondition(self.obj27, rootNode)
    self.obj27.postAction( rootNode.CREATE )

    self.obj28=CD_Class3(self)
    self.obj28.isGraphObjectVisual = True

    if(hasattr(self.obj28, '_setHierarchicalLink')):
      self.obj28._setHierarchicalLink(False)

    # QOCA
    self.obj28.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <---- Remove this to use QOCA\n\n""" Get the high level constraint helper and solver """\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.fixedWidth(self.graphObject_, self.graphObject_.sizeX)\noc.fixedHeight(self.graphObject_, self.graphObject_.sizeY)\n\n'))

    # Graphical_Appearance
    self.obj28.Graphical_Appearance.setValue( ('ROSMessage', self.obj28))

    # name
    self.obj28.name.setValue('ROSMessage')

    # attributes
    self.obj28.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj28.attributes.setValue(lcobj2)

    # Abstract
    self.obj28.Abstract.setValue((None, 1))
    self.obj28.Abstract.config = 0

    # cardinality
    self.obj28.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSType', (('Source', 'Destination'), 1), '0', '1'))
    lcobj2.append(cobj2)
    self.obj28.cardinality.setValue(lcobj2)

    # display
    self.obj28.display.setValue('Multiplicities:\n  - From ROSType: 0 to 1\n')
    self.obj28.display.setHeight(15)

    # Actions
    self.obj28.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj28.Actions.setValue(lcobj2)

    # Constraints
    self.obj28.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj28.Constraints.setValue(lcobj2)

    self.obj28.graphClass_= graph_CD_Class3
    if self.genGraphics:
       new_obj = graph_CD_Class3(420.0,400.0,self.obj28)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Class3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.0, 1.0]
    else: new_obj = None
    self.obj28.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj28)
    self.globalAndLocalPostcondition(self.obj28, rootNode)
    self.obj28.postAction( rootNode.CREATE )

    self.obj29=CD_Class3(self)
    self.obj29.isGraphObjectVisual = True

    if(hasattr(self.obj29, '_setHierarchicalLink')):
      self.obj29._setHierarchicalLink(False)

    # QOCA
    self.obj29.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <---- Remove this to use QOCA\n\n""" Get the high level constraint helper and solver """\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.fixedWidth(self.graphObject_, self.graphObject_.sizeX)\noc.fixedHeight(self.graphObject_, self.graphObject_.sizeY)\n\n'))

    # Graphical_Appearance
    self.obj29.Graphical_Appearance.setValue( ('ROSTwist', self.obj29))

    # name
    self.obj29.name.setValue('ROSTwist')

    # attributes
    self.obj29.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    cobj2=ATOM3Attribute(self.types)
    cobj2.setValue(('angular', 'List', None, ('Key', 0), ('Direct Editing', 1)))
    cobj2.initialValue=ATOM3List([ 0, 0, 0, self.types],ATOM3String)
    lcobj3=[]
    cobj3=ATOM3String('x', 20)
    lcobj3.append(cobj3)
    cobj3=ATOM3String('y', 20)
    lcobj3.append(cobj3)
    cobj3=ATOM3String('z', 20)
    lcobj3.append(cobj3)
    cobj2.initialValue.setValue(lcobj3)
    cobj2.isDerivedAttribute = False
    lcobj2.append(cobj2)
    cobj2=ATOM3Attribute(self.types)
    cobj2.setValue(('linear', 'List', None, ('Key', 0), ('Direct Editing', 1)))
    cobj2.initialValue=ATOM3List([ 0, 0, 0, self.types],ATOM3String)
    lcobj3=[]
    cobj3=ATOM3String('x', 20)
    lcobj3.append(cobj3)
    cobj3=ATOM3String('y', 20)
    lcobj3.append(cobj3)
    cobj3=ATOM3String('z', 20)
    lcobj3.append(cobj3)
    cobj2.initialValue.setValue(lcobj3)
    cobj2.isDerivedAttribute = False
    lcobj2.append(cobj2)
    self.obj29.attributes.setValue(lcobj2)

    # Abstract
    self.obj29.Abstract.setValue((None, 0))
    self.obj29.Abstract.config = 0

    # cardinality
    self.obj29.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    self.obj29.cardinality.setValue(lcobj2)

    # display
    self.obj29.display.setValue('Attributes:\n  - angular :: List\n  - linear :: List\n')
    self.obj29.display.setHeight(15)

    # Actions
    self.obj29.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj29.Actions.setValue(lcobj2)

    # Constraints
    self.obj29.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj29.Constraints.setValue(lcobj2)

    self.obj29.graphClass_= graph_CD_Class3
    if self.genGraphics:
       new_obj = graph_CD_Class3(300.0,600.0,self.obj29)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Class3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.0, 1.0]
    else: new_obj = None
    self.obj29.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj29)
    self.globalAndLocalPostcondition(self.obj29, rootNode)
    self.obj29.postAction( rootNode.CREATE )

    self.obj30=CD_Class3(self)
    self.obj30.isGraphObjectVisual = True

    if(hasattr(self.obj30, '_setHierarchicalLink')):
      self.obj30._setHierarchicalLink(False)

    # QOCA
    self.obj30.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <---- Remove this to use QOCA\n\n""" Get the high level constraint helper and solver """\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.fixedWidth(self.graphObject_, self.graphObject_.sizeX)\noc.fixedHeight(self.graphObject_, self.graphObject_.sizeY)\n\n'))

    # Graphical_Appearance
    self.obj30.Graphical_Appearance.setValue( ('ROSBoolean', self.obj30))

    # name
    self.obj30.name.setValue('ROSBoolean')

    # attributes
    self.obj30.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    cobj2=ATOM3Attribute(self.types)
    cobj2.setValue(('data', 'String', None, ('Key', 0), ('Direct Editing', 1)))
    cobj2.initialValue=ATOM3String('', 20)
    cobj2.isDerivedAttribute = False
    lcobj2.append(cobj2)
    self.obj30.attributes.setValue(lcobj2)

    # Abstract
    self.obj30.Abstract.setValue((None, 0))
    self.obj30.Abstract.config = 0

    # cardinality
    self.obj30.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    self.obj30.cardinality.setValue(lcobj2)

    # display
    self.obj30.display.setValue('Attributes:\n  - data :: String\n')
    self.obj30.display.setHeight(15)

    # Actions
    self.obj30.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj30.Actions.setValue(lcobj2)

    # Constraints
    self.obj30.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj30.Constraints.setValue(lcobj2)

    self.obj30.graphClass_= graph_CD_Class3
    if self.genGraphics:
       new_obj = graph_CD_Class3(580.0,600.0,self.obj30)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Class3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.0, 1.0]
    else: new_obj = None
    self.obj30.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj30)
    self.globalAndLocalPostcondition(self.obj30, rootNode)
    self.obj30.postAction( rootNode.CREATE )

    self.obj31=CD_Association3(self)
    self.obj31.isGraphObjectVisual = True

    if(hasattr(self.obj31, '_setHierarchicalLink')):
      self.obj31._setHierarchicalLink(False)

    # QOCA
    self.obj31.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <--- Remove this if you want to use QOCA\n\n# Get the high level constraint helper and solver\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n# Constraint only makes sense if there exists 2 objects connected to this link\nif(not (self.in_connections_ and self.out_connections_)): return\n\n# Get the graphical objects (subclass of graphEntity/graphLink) \ngraphicalObjectLink = self.graphObject_\ngraphicalObjectSource = self.in_connections_[0].graphObject_\ngraphicalObjectTarget = self.out_connections_[0].graphObject_\nobjTuple = (graphicalObjectSource, graphicalObjectTarget, graphicalObjectLink)\n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.LeftExactDistance(objTuple, 20)\noc.resolve() # Resolve immediately after creating entity & constraint \n\n'))

    # Graphical_Appearance
    self.obj31.Graphical_Appearance.setValue( ('ROSPublish', self.obj31))
    self.obj31.Graphical_Appearance.linkInfo=linkEditor(self,self.obj31.Graphical_Appearance.semObject, "ROSPublish")
    self.obj31.Graphical_Appearance.linkInfo.FirstLink= stickylink()
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.arrow=ATOM3Boolean()
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.arrow.setValue((' ', 0))
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.arrow.config = 0
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.arrowShape1=ATOM3Integer(8)
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.arrowShape2=ATOM3Integer(10)
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.arrowShape3=ATOM3Integer(3)
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.decoration=ATOM3Appearance()
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.decoration.setValue( ('ROSPublish_1stLink', self.obj31.Graphical_Appearance.linkInfo.FirstLink))
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment= widthXfillXdecoration()
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.width=ATOM3Integer(2)
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.fill=ATOM3String('black', 20)
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.stipple=ATOM3String('', 20)
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.arrow=ATOM3Boolean()
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.arrow.setValue((' ', 0))
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.arrow.config = 0
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.arrowShape1=ATOM3Integer(8)
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.arrowShape2=ATOM3Integer(10)
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.arrowShape3=ATOM3Integer(3)
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.decoration=ATOM3Appearance()
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.decoration.setValue( ('ROSPublish_1stSegment', self.obj31.Graphical_Appearance.linkInfo.FirstSegment))
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.decoration_Position=ATOM3Enum(['Up', 'Down', 'Middle', 'No decoration'],3,0)
    self.obj31.Graphical_Appearance.linkInfo.Center=ATOM3Appearance()
    self.obj31.Graphical_Appearance.linkInfo.Center.setValue( ('ROSPublish_Center', self.obj31.Graphical_Appearance.linkInfo))
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment= widthXfillXdecoration()
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.width=ATOM3Integer(2)
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.fill=ATOM3String('black', 20)
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.stipple=ATOM3String('', 20)
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.arrow=ATOM3Boolean()
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.arrow.setValue((' ', 0))
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.arrow.config = 0
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.arrowShape1=ATOM3Integer(8)
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.arrowShape2=ATOM3Integer(10)
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.arrowShape3=ATOM3Integer(3)
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.decoration=ATOM3Appearance()
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.decoration.setValue( ('ROSPublish_2ndSegment', self.obj31.Graphical_Appearance.linkInfo.SecondSegment))
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.decoration_Position=ATOM3Enum(['Up', 'Down', 'Middle', 'No decoration'],3,0)
    self.obj31.Graphical_Appearance.linkInfo.SecondLink= stickylink()
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.arrow=ATOM3Boolean()
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.arrow.setValue((' ', 1))
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.arrow.config = 0
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.arrowShape1=ATOM3Integer(8)
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.arrowShape2=ATOM3Integer(10)
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.arrowShape3=ATOM3Integer(3)
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.decoration=ATOM3Appearance()
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.decoration.setValue( ('ROSPublish_2ndLink', self.obj31.Graphical_Appearance.linkInfo.SecondLink))
    self.obj31.Graphical_Appearance.linkInfo.FirstLink.decoration.semObject=self.obj31.Graphical_Appearance.semObject
    self.obj31.Graphical_Appearance.linkInfo.FirstSegment.decoration.semObject=self.obj31.Graphical_Appearance.semObject
    self.obj31.Graphical_Appearance.linkInfo.Center.semObject=self.obj31.Graphical_Appearance.semObject
    self.obj31.Graphical_Appearance.linkInfo.SecondSegment.decoration.semObject=self.obj31.Graphical_Appearance.semObject
    self.obj31.Graphical_Appearance.linkInfo.SecondLink.decoration.semObject=self.obj31.Graphical_Appearance.semObject

    # name
    self.obj31.name.setValue('ROSPublish')

    # displaySelect
    self.obj31.displaySelect.setValue( (['attributes', 'constraints', 'actions', 'cardinality'], [0, 0, 0, 0]) )
    self.obj31.displaySelect.config = 0

    # attributes
    self.obj31.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj31.attributes.setValue(lcobj2)

    # cardinality
    self.obj31.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSTopic', (('Source', 'Destination'), 0), '0', '1'))
    lcobj2.append(cobj2)
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSNode', (('Source', 'Destination'), 1), '0', '1'))
    lcobj2.append(cobj2)
    self.obj31.cardinality.setValue(lcobj2)

    # display
    self.obj31.display.setValue('Constraints:\n  > publishOnce\nMultiplicities:\n  - To ROSTopic: 0 to 1\n  - From ROSNode: 0 to 1\n')
    self.obj31.display.setHeight(15)

    # Actions
    self.obj31.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj31.Actions.setValue(lcobj2)

    # Constraints
    self.obj31.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    cobj2=ATOM3Constraint()
    cobj2.setValue(('publishOnce', (['Python', 'OCL'], 1), (['PREcondition', 'POSTcondition'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]), 'if len(self.in_connections_) == 0: return\nnode = self.in_connections_[0]\nif len(node.out_connections_) == 0: return\n\nfor edge in node.out_connections_:\n  for edge2 in node.out_connections_:\n    if len(edge2.out_connections_) == 0: return\n    if len(edge.in_connections_) == 0: return\n    if len(edge2.in_connections_) == 0: return\n    if edge != edge2 and edge.in_connections_[0] == edge2.in_connections_[0] and edge.out_connections_[0] == edge2.out_connections_[0]:\n      return ("Cannot publish to same topic twice.", self)\n'))
    lcobj2.append(cobj2)
    self.obj31.Constraints.setValue(lcobj2)

    self.obj31.graphClass_= graph_CD_Association3
    if self.genGraphics:
       new_obj = graph_CD_Association3(213.25,273.25,self.obj31)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Association3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.2530000000000001, 1.185483870967742]
    else: new_obj = None
    self.obj31.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj31)
    self.globalAndLocalPostcondition(self.obj31, rootNode)
    self.obj31.postAction( rootNode.CREATE )

    self.obj32=CD_Association3(self)
    self.obj32.isGraphObjectVisual = True

    if(hasattr(self.obj32, '_setHierarchicalLink')):
      self.obj32._setHierarchicalLink(False)

    # QOCA
    self.obj32.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <--- Remove this if you want to use QOCA\n\n# Get the high level constraint helper and solver\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n# Constraint only makes sense if there exists 2 objects connected to this link\nif(not (self.in_connections_ and self.out_connections_)): return\n\n# Get the graphical objects (subclass of graphEntity/graphLink) \ngraphicalObjectLink = self.graphObject_\ngraphicalObjectSource = self.in_connections_[0].graphObject_\ngraphicalObjectTarget = self.out_connections_[0].graphObject_\nobjTuple = (graphicalObjectSource, graphicalObjectTarget, graphicalObjectLink)\n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.LeftExactDistance(objTuple, 20)\noc.resolve() # Resolve immediately after creating entity & constraint \n\n'))

    # Graphical_Appearance
    self.obj32.Graphical_Appearance.setValue( ('ROSSubscribe', self.obj32))
    self.obj32.Graphical_Appearance.linkInfo=linkEditor(self,self.obj32.Graphical_Appearance.semObject, "ROSSubscribe")
    self.obj32.Graphical_Appearance.linkInfo.FirstLink= stickylink()
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.arrow=ATOM3Boolean()
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.arrow.setValue((' ', 0))
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.arrow.config = 0
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.arrowShape1=ATOM3Integer(8)
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.arrowShape2=ATOM3Integer(10)
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.arrowShape3=ATOM3Integer(3)
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.decoration=ATOM3Appearance()
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.decoration.setValue( ('ROSSubscribe_1stLink', self.obj32.Graphical_Appearance.linkInfo.FirstLink))
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment= widthXfillXdecoration()
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.width=ATOM3Integer(2)
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.fill=ATOM3String('black', 20)
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.stipple=ATOM3String('', 20)
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.arrow=ATOM3Boolean()
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.arrow.setValue((' ', 0))
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.arrow.config = 0
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.arrowShape1=ATOM3Integer(8)
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.arrowShape2=ATOM3Integer(10)
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.arrowShape3=ATOM3Integer(3)
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.decoration=ATOM3Appearance()
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.decoration.setValue( ('ROSSubscribe_1stSegment', self.obj32.Graphical_Appearance.linkInfo.FirstSegment))
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.decoration_Position=ATOM3Enum(['Up', 'Down', 'Middle', 'No decoration'],3,0)
    self.obj32.Graphical_Appearance.linkInfo.Center=ATOM3Appearance()
    self.obj32.Graphical_Appearance.linkInfo.Center.setValue( ('ROSSubscribe_Center', self.obj32.Graphical_Appearance.linkInfo))
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment= widthXfillXdecoration()
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.width=ATOM3Integer(2)
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.fill=ATOM3String('black', 20)
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.stipple=ATOM3String('', 20)
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.arrow=ATOM3Boolean()
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.arrow.setValue((' ', 0))
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.arrow.config = 0
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.arrowShape1=ATOM3Integer(8)
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.arrowShape2=ATOM3Integer(10)
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.arrowShape3=ATOM3Integer(3)
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.decoration=ATOM3Appearance()
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.decoration.setValue( ('ROSSubscribe_2ndSegment', self.obj32.Graphical_Appearance.linkInfo.SecondSegment))
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.decoration_Position=ATOM3Enum(['Up', 'Down', 'Middle', 'No decoration'],3,0)
    self.obj32.Graphical_Appearance.linkInfo.SecondLink= stickylink()
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.arrow=ATOM3Boolean()
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.arrow.setValue((' ', 1))
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.arrow.config = 0
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.arrowShape1=ATOM3Integer(8)
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.arrowShape2=ATOM3Integer(10)
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.arrowShape3=ATOM3Integer(3)
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.decoration=ATOM3Appearance()
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.decoration.setValue( ('ROSSubscribe_2ndLink', self.obj32.Graphical_Appearance.linkInfo.SecondLink))
    self.obj32.Graphical_Appearance.linkInfo.FirstLink.decoration.semObject=self.obj32.Graphical_Appearance.semObject
    self.obj32.Graphical_Appearance.linkInfo.FirstSegment.decoration.semObject=self.obj32.Graphical_Appearance.semObject
    self.obj32.Graphical_Appearance.linkInfo.Center.semObject=self.obj32.Graphical_Appearance.semObject
    self.obj32.Graphical_Appearance.linkInfo.SecondSegment.decoration.semObject=self.obj32.Graphical_Appearance.semObject
    self.obj32.Graphical_Appearance.linkInfo.SecondLink.decoration.semObject=self.obj32.Graphical_Appearance.semObject

    # name
    self.obj32.name.setValue('ROSSubscribe')

    # displaySelect
    self.obj32.displaySelect.setValue( (['attributes', 'constraints', 'actions', 'cardinality'], [0, 0, 0, 0]) )
    self.obj32.displaySelect.config = 0

    # attributes
    self.obj32.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj32.attributes.setValue(lcobj2)

    # cardinality
    self.obj32.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSNode', (('Source', 'Destination'), 0), '0', '1'))
    lcobj2.append(cobj2)
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSTopic', (('Source', 'Destination'), 1), '0', '1'))
    lcobj2.append(cobj2)
    self.obj32.cardinality.setValue(lcobj2)

    # display
    self.obj32.display.setValue('Constraints:\n  > subscribeOnce\nMultiplicities:\n  - To ROSNode: 0 to 1\n  - From ROSTopic: 0 to 1\n')
    self.obj32.display.setHeight(15)

    # Actions
    self.obj32.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj32.Actions.setValue(lcobj2)

    # Constraints
    self.obj32.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    cobj2=ATOM3Constraint()
    cobj2.setValue(('subscribeOnce', (['Python', 'OCL'], 1), (['PREcondition', 'POSTcondition'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]), 'if len(self.out_connections_) == 0: return\nnode = self.out_connections_[0]\nif len(node.in_connections_) == 0: return\n\nfor edge in node.in_connections_:\n  for edge2 in node.in_connections_:\n    if len(edge2.in_connections_) == 0: return\n    if len(edge.out_connections_) == 0: return\n    if len(edge2.out_connections_) == 0: return\n    if edge != edge2 and edge.out_connections_[0] == edge2.out_connections_[0] and edge.in_connections_[0] == edge2.in_connections_[0]:\n      return ("Cannot subscribe to same topic twice.", self)\n\n'))
    lcobj2.append(cobj2)
    self.obj32.Constraints.setValue(lcobj2)

    self.obj32.graphClass_= graph_CD_Association3
    if self.genGraphics:
       new_obj = graph_CD_Association3(495.334960938,100.5,self.obj32)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Association3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.26, 1.185483870967742]
    else: new_obj = None
    self.obj32.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj32)
    self.globalAndLocalPostcondition(self.obj32, rootNode)
    self.obj32.postAction( rootNode.CREATE )

    self.obj33=CD_Association3(self)
    self.obj33.isGraphObjectVisual = True

    if(hasattr(self.obj33, '_setHierarchicalLink')):
      self.obj33._setHierarchicalLink(False)

    # QOCA
    self.obj33.QOCA.setValue(('QOCA', (['Python', 'OCL'], 1), (['PREaction', 'POSTaction'], 1), (['EDIT', 'SAVE', 'CREATE', 'CONNECT', 'DELETE', 'DISCONNECT', 'TRANSFORM', 'SELECT', 'DRAG', 'DROP', 'MOVE'], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]), '"""\nQOCA Constraint Template\nNOTE: DO NOT select a POST/PRE action trigger\nConstraints will be added/removed in a logical manner by other mechanisms.\n"""\nreturn # <--- Remove this if you want to use QOCA\n\n# Get the high level constraint helper and solver\nfrom Qoca.atom3constraints.OffsetConstraints import OffsetConstraints\noc = OffsetConstraints(self.parent.qocaSolver)  \n\n# Constraint only makes sense if there exists 2 objects connected to this link\nif(not (self.in_connections_ and self.out_connections_)): return\n\n# Get the graphical objects (subclass of graphEntity/graphLink) \ngraphicalObjectLink = self.graphObject_\ngraphicalObjectSource = self.in_connections_[0].graphObject_\ngraphicalObjectTarget = self.out_connections_[0].graphObject_\nobjTuple = (graphicalObjectSource, graphicalObjectTarget, graphicalObjectLink)\n\n"""\nExample constraint, see Kernel/QOCA/atom3constraints/OffsetConstraints.py\nFor more types of constraints\n"""\noc.LeftExactDistance(objTuple, 20)\noc.resolve() # Resolve immediately after creating entity & constraint \n\n'))

    # Graphical_Appearance
    self.obj33.Graphical_Appearance.setValue( ('ROSType', self.obj33))
    self.obj33.Graphical_Appearance.linkInfo=linkEditor(self,self.obj33.Graphical_Appearance.semObject, "ROSType")
    self.obj33.Graphical_Appearance.linkInfo.FirstLink= stickylink()
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.arrow=ATOM3Boolean()
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.arrow.setValue((' ', 0))
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.arrow.config = 0
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.arrowShape1=ATOM3Integer(8)
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.arrowShape2=ATOM3Integer(10)
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.arrowShape3=ATOM3Integer(3)
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.decoration=ATOM3Appearance()
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.decoration.setValue( ('ROSType_1stLink', self.obj33.Graphical_Appearance.linkInfo.FirstLink))
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment= widthXfillXdecoration()
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.width=ATOM3Integer(2)
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.fill=ATOM3String('black', 20)
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.stipple=ATOM3String('', 20)
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.arrow=ATOM3Boolean()
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.arrow.setValue((' ', 0))
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.arrow.config = 0
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.arrowShape1=ATOM3Integer(8)
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.arrowShape2=ATOM3Integer(10)
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.arrowShape3=ATOM3Integer(3)
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.decoration=ATOM3Appearance()
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.decoration.setValue( ('ROSType_1stSegment', self.obj33.Graphical_Appearance.linkInfo.FirstSegment))
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.decoration_Position=ATOM3Enum(['Up', 'Down', 'Middle', 'No decoration'],3,0)
    self.obj33.Graphical_Appearance.linkInfo.Center=ATOM3Appearance()
    self.obj33.Graphical_Appearance.linkInfo.Center.setValue( ('ROSType_Center', self.obj33.Graphical_Appearance.linkInfo))
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment= widthXfillXdecoration()
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.width=ATOM3Integer(2)
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.fill=ATOM3String('black', 20)
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.stipple=ATOM3String('', 20)
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.arrow=ATOM3Boolean()
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.arrow.setValue((' ', 0))
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.arrow.config = 0
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.arrowShape1=ATOM3Integer(8)
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.arrowShape2=ATOM3Integer(10)
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.arrowShape3=ATOM3Integer(3)
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.decoration=ATOM3Appearance()
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.decoration.setValue( ('ROSType_2ndSegment', self.obj33.Graphical_Appearance.linkInfo.SecondSegment))
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.decoration_Position=ATOM3Enum(['Up', 'Down', 'Middle', 'No decoration'],3,0)
    self.obj33.Graphical_Appearance.linkInfo.SecondLink= stickylink()
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.arrow=ATOM3Boolean()
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.arrow.setValue((' ', 1))
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.arrow.config = 0
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.arrowShape1=ATOM3Integer(8)
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.arrowShape2=ATOM3Integer(10)
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.arrowShape3=ATOM3Integer(3)
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.decoration=ATOM3Appearance()
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.decoration.setValue( ('ROSType_2ndLink', self.obj33.Graphical_Appearance.linkInfo.SecondLink))
    self.obj33.Graphical_Appearance.linkInfo.FirstLink.decoration.semObject=self.obj33.Graphical_Appearance.semObject
    self.obj33.Graphical_Appearance.linkInfo.FirstSegment.decoration.semObject=self.obj33.Graphical_Appearance.semObject
    self.obj33.Graphical_Appearance.linkInfo.Center.semObject=self.obj33.Graphical_Appearance.semObject
    self.obj33.Graphical_Appearance.linkInfo.SecondSegment.decoration.semObject=self.obj33.Graphical_Appearance.semObject
    self.obj33.Graphical_Appearance.linkInfo.SecondLink.decoration.semObject=self.obj33.Graphical_Appearance.semObject

    # name
    self.obj33.name.setValue('ROSType')

    # displaySelect
    self.obj33.displaySelect.setValue( (['attributes', 'constraints', 'actions', 'cardinality'], [0, 0, 0, 0]) )
    self.obj33.displaySelect.config = 0

    # attributes
    self.obj33.attributes.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj33.attributes.setValue(lcobj2)

    # cardinality
    self.obj33.cardinality.setActionFlags([ 0, 1, 0, 0])
    lcobj2 =[]
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSMessage', (('Source', 'Destination'), 0), '0', '1'))
    lcobj2.append(cobj2)
    cobj2=ATOM3Connection()
    cobj2.setValue(('ROSTopic', (('Source', 'Destination'), 1), '0', '1'))
    lcobj2.append(cobj2)
    self.obj33.cardinality.setValue(lcobj2)

    # display
    self.obj33.display.setValue('Multiplicities:\n  - To ROSMessage: 0 to 1\n  - From ROSTopic: 0 to 1\n')
    self.obj33.display.setHeight(15)

    # Actions
    self.obj33.Actions.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj33.Actions.setValue(lcobj2)

    # Constraints
    self.obj33.Constraints.setActionFlags([ 1, 1, 1, 0])
    lcobj2 =[]
    self.obj33.Constraints.setValue(lcobj2)

    self.obj33.graphClass_= graph_CD_Association3
    if self.genGraphics:
       new_obj = graph_CD_Association3(775.546875,369.0,self.obj33)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Association3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
       new_obj.layConstraints['scale'] = [1.309, 1.0]
    else: new_obj = None
    self.obj33.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj33)
    self.globalAndLocalPostcondition(self.obj33, rootNode)
    self.obj33.postAction( rootNode.CREATE )

    self.obj34=CD_Inheritance3(self)
    self.obj34.isGraphObjectVisual = True

    if(hasattr(self.obj34, '_setHierarchicalLink')):
      self.obj34._setHierarchicalLink(False)

    self.obj34.graphClass_= graph_CD_Inheritance3
    if self.genGraphics:
       new_obj = graph_CD_Inheritance3(456.0,571.0,self.obj34)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Inheritance3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
    else: new_obj = None
    self.obj34.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj34)
    self.globalAndLocalPostcondition(self.obj34, rootNode)
    self.obj34.postAction( rootNode.CREATE )

    self.obj35=CD_Inheritance3(self)
    self.obj35.isGraphObjectVisual = True

    if(hasattr(self.obj35, '_setHierarchicalLink')):
      self.obj35._setHierarchicalLink(False)

    self.obj35.graphClass_= graph_CD_Inheritance3
    if self.genGraphics:
       new_obj = graph_CD_Inheritance3(596.0,571.0,self.obj35)
       new_obj.DrawObject(self.UMLmodel)
       self.UMLmodel.addtag_withtag("CD_Inheritance3", new_obj.tag)
       new_obj.layConstraints = dict() # Graphical Layout Constraints 
    else: new_obj = None
    self.obj35.graphObject_ = new_obj

    # Add node to the root: rootNode
    rootNode.addNode(self.obj35)
    self.globalAndLocalPostcondition(self.obj35, rootNode)
    self.obj35.postAction( rootNode.CREATE )

    # Connections for obj26 (graphObject_: Obj0) named ROSNode
    self.drawConnections(
(self.obj26,self.obj31,[162.0, 181.0, 157.0, 244.5, 213.25, 273.25],"true", 3) )
    # Connections for obj27 (graphObject_: Obj1) named ROSTopic
    self.drawConnections(
(self.obj27,self.obj32,[545.1875, 201.1803278688525, 552.71875, 123.5, 495.334960938, 100.5],"true", 3),
(self.obj27,self.obj33,[625.98828125, 331.3114754098361, 775.546875, 369.0],"true", 2) )
    # Connections for obj28 (graphObject_: Obj2) named ROSMessage
    self.drawConnections(
 )
    # Connections for obj29 (graphObject_: Obj3) named ROSTwist
    self.drawConnections(
(self.obj29,self.obj34,[456.0, 601.0, 456.0, 571.0],"true", 2) )
    # Connections for obj30 (graphObject_: Obj4) named ROSBoolean
    self.drawConnections(
(self.obj30,self.obj35,[616.0, 601.0, 596.0, 571.0],"true", 2) )
    # Connections for obj31 (graphObject_: Obj5) named ROSPublish
    self.drawConnections(
(self.obj31,self.obj27,[213.25, 273.25, 269.5, 302.0, 421.29296875, 304.20081967213116],"true", 3) )
    # Connections for obj32 (graphObject_: Obj7) named ROSSubscribe
    self.drawConnections(
(self.obj32,self.obj26,[495.334960938, 100.5, 437.951171875, 77.5, 344.24609375, 81.0],"true", 3) )
    # Connections for obj33 (graphObject_: Obj9) named ROSType
    self.drawConnections(
(self.obj33,self.obj28,[775.546875, 369.0, 611.0, 441.0],"true", 2) )
    # Connections for obj34 (graphObject_: Obj11) of type CD_Inheritance3
    self.drawConnections(
(self.obj34,self.obj28,[456.0, 571.0, 456.0, 541.0],"true", 2) )
    # Connections for obj35 (graphObject_: Obj13) of type CD_Inheritance3
    self.drawConnections(
(self.obj35,self.obj28,[596.0, 571.0, 576.0, 541.0],"true", 2) )

newfunction = ROSApp_MDL

loadedMMName = 'CD_ClassDiagramsV3_META'

atom3version = '0.3'
