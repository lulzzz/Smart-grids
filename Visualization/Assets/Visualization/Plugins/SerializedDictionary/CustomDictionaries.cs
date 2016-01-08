using UnityEngine;
using UnityEngine.UI;
using System;


[Serializable]
public class GeneratorSpriteDictionary : SerializableDictionary<GeneratorType, Sprite> { }

[Serializable]
public class ApplianceSpriteDictionary : SerializableDictionary<ApplianceType, Sprite> { }
