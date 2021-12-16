import json
from types import SimpleNamespace

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

class DataObject: 
  def __init__(self, location, datetime, sensorType, value):
    self.location = location
    self.datetime = datetime
    self.sensorType = sensorType
    self.value = value
  


  def printData(self):
    print(self.location + self.datetime + self.sensorType + self.value)  

  def isValueValid(self):
    if self.value == 'NULL' or self.value =='':
      print("null!")
      return False
    elif self.sensorType == 'pH':
      if float(self.value) < 0 :
        return False
      elif float(self.value) >14 :
        return False
      else:
        return True  
    elif self.sensorType == 'temperature':
      if float(self.value) < -50:
        return False
      elif float(self.value) > 100:
        return False
      else:
        return True
    elif self.sensorType == 'rainFall':
      if float(self.value) < 0:
        return False
      elif float(self.value) > 500:
        return False
      else:
        return True
    else:
     return False

  def uploadDataToFirebase(self, docName):
      doc_ref = db.collection(u'farm_data').document(docName)
      doc_ref.collection(u'data').add({
        u'location':self.location,
        u'datetime':self.datetime,
        u'sensorType':self.sensorType,
        u'value':self.value
        })

# Use a service account
cred = credentials.Certificate('credentials.json')
firebase_admin.initialize_app(cred)

db = firestore.client()


def uploadJsonFile(jsonFile):
  with open(jsonFile +'.json') as data_file:
    data = json.load(data_file)
    counter = 0
    discardCounter = 0
  for v in data:
       counter = counter + 1
       dataObject = DataObject(v['location'], v['datetime'], v['sensorType'],v['value'])
       dataObject.printData()
       
       if(dataObject.isValueValid()):
        print(counter, '/' , len(data))
        dataObject.uploadDataToFirebase(jsonFile)
       else:
        discardCounter = discardCounter + 1
        print("Value skipped! Discard counter : ", discardCounter)

  print("Discard counter:", discardCounter)

#uploadJsonFile(u'Nooras_farm')
uploadJsonFile(u'ossi_farm')
#uploadJsonFile(u'PartialTech')
#uploadJsonFile(u'friman_metsola')