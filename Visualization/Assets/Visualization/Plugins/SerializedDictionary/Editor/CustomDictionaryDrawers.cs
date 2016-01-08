using UnityEditor;
using UnityEngine;

[CustomPropertyDrawer(typeof(GeneratorSpriteDictionary))]
public class GeneratorSpriteDictionaryDrawer : DictionaryDrawer<GeneratorType, Sprite> { }

[CustomPropertyDrawer(typeof(ApplianceSpriteDictionary))]
public class ApplianceSpriteDictionaryDrawer : DictionaryDrawer<ApplianceType, Sprite> { }
