import unittest

import firestore_import as myFile

class TestValueValidation(unittest.TestCase):

    def test_ph(self):
    	validDataObject1 = myFile.DataObject('location','datetime','pH','0')
    	validDataObject2 = myFile.DataObject('location','datetime','pH','14')
    	validDataObject3 = myFile.DataObject('location','datetime','pH','6.5')
    	validDataObject4 = myFile.DataObject('location','datetime','pH','7')
    	validDataObject5 = myFile.DataObject('location','datetime','pH','8.33')
    	invalidDataObject1 = myFile.DataObject('location','datetime','pH','-1')
    	invalidDataObject2 = myFile.DataObject('location','datetime','pH','15')
    	invalidDataObject3 = myFile.DataObject('location','datetime','pH','-33')
    	invalidDataObject4 = myFile.DataObject('location','datetime','pH','155')
    	self.assertTrue(validDataObject1.isValueValid())
    	self.assertTrue(validDataObject2.isValueValid())
    	self.assertTrue(validDataObject3.isValueValid())
    	self.assertTrue(validDataObject4.isValueValid())
    	self.assertTrue(validDataObject5.isValueValid())
    	self.assertFalse(invalidDataObject1.isValueValid())
    	self.assertFalse(invalidDataObject2.isValueValid())
    	self.assertFalse(invalidDataObject3.isValueValid())
    	self.assertFalse(invalidDataObject4.isValueValid())

    def test_temperature(self):
    	validDataObject1 = myFile.DataObject('location','datetime','temperature','-50')
    	validDataObject2 = myFile.DataObject('location','datetime','temperature','-49')
    	validDataObject3 = myFile.DataObject('location','datetime','temperature','12')
    	validDataObject4 = myFile.DataObject('location','datetime','temperature','13.55')
    	validDataObject5 = myFile.DataObject('location','datetime','temperature','100')
    	validDataObject6 = myFile.DataObject('location','datetime','temperature','88')
    	invalidDataObject1 = myFile.DataObject('location','datetime','temperature','-51')
    	invalidDataObject2 = myFile.DataObject('location','datetime','temperature','-123.33')
    	invalidDataObject3 = myFile.DataObject('location','datetime','temperature','5555.55')
    	invalidDataObject4 = myFile.DataObject('location','datetime','temperature','100.5')
    	invalidDataObject5 = myFile.DataObject('location','datetime','temperature','9999.92929')

    	self.assertTrue(validDataObject1.isValueValid())
    	self.assertTrue(validDataObject2.isValueValid())
    	self.assertTrue(validDataObject3.isValueValid())
    	self.assertTrue(validDataObject4.isValueValid())
    	self.assertTrue(validDataObject5.isValueValid())
    	self.assertTrue(validDataObject6.isValueValid())
    	self.assertFalse(invalidDataObject1.isValueValid())
    	self.assertFalse(invalidDataObject2.isValueValid())
    	self.assertFalse(invalidDataObject3.isValueValid())
    	self.assertFalse(invalidDataObject4.isValueValid())
    	self.assertFalse(invalidDataObject5.isValueValid())



    def test_rainFall(self):
    	validDataObject1 = myFile.DataObject('location','datetime','rainFall','0')
    	validDataObject2 = myFile.DataObject('location','datetime','rainFall','12.2')
    	validDataObject3 = myFile.DataObject('location','datetime','rainFall','133.333')
    	validDataObject4 = myFile.DataObject('location','datetime','rainFall','500')
    	validDataObject5 = myFile.DataObject('location','datetime','rainFall','420.69')
    	validDataObject6 = myFile.DataObject('location','datetime','rainFall','69.69')
    	invalidDataObject1 = myFile.DataObject('location','datetime','rainFall','-1')
    	invalidDataObject2 = myFile.DataObject('location','datetime','rainFall','501')
    	invalidDataObject3 = myFile.DataObject('location','datetime','rainFall','555.55')
    	invalidDataObject4 = myFile.DataObject('location','datetime','rainFall','5231')
    	invalidDataObject5 = myFile.DataObject('location','datetime','rainFall','-1233')

    	self.assertTrue(validDataObject1.isValueValid())
    	self.assertTrue(validDataObject2.isValueValid())
    	self.assertTrue(validDataObject3.isValueValid())
    	self.assertTrue(validDataObject4.isValueValid())
    	self.assertTrue(validDataObject5.isValueValid())
    	self.assertTrue(validDataObject6.isValueValid())
    	self.assertFalse(invalidDataObject1.isValueValid())
    	self.assertFalse(invalidDataObject2.isValueValid())
    	self.assertFalse(invalidDataObject3.isValueValid())
    	self.assertFalse(invalidDataObject4.isValueValid())
    	self.assertFalse(invalidDataObject5.isValueValid())


if __name__ == '__main__':
    unittest.main(verbosity = 2)