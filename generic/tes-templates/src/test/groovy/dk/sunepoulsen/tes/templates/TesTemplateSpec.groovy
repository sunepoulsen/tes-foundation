package dk.sunepoulsen.tes.templates

import org.apache.velocity.app.VelocityEngine
import spock.lang.Specification

class TesTemplateSpec extends Specification {

    private VelocityEngine velocityEngine

    void setup() {
        velocityEngine = VelocityEngineFactory.newInstance()
    }

    void "Test produce template with simple types"() {
        given:
            TesTemplate template = new TesTemplate(velocityEngine, 'templates/simple-types.md', {
                [
                    'int': 5,
                    'double': 6.7,
                    'boolean': true,
                    'text': 'This is a text'
                ]
            })

        when:
            StringWriter result = template.produce()

        then:
            result.toString() ==
"""# Test of Velocity Engine with simple types

- int: 5
- double: 6.7
- boolean: true
- text: This is a text
"""
    }

    void "Test produce template with iteration types"() {
        given:
            TesTemplate template = new TesTemplate(velocityEngine, 'templates/iteration-types.md', {
                [
                    'list': ['Item 1', 'Item 2', 'Item 3'],
                    'map': [
                        'int': 5,
                        'double': 6.7,
                        'boolean': true,
                        'text': 'This is a text'
                    ]
                ]
            })

        when:
            StringWriter result = template.produce()

        then:
            result.toString() ==
"""# Test of Velocity Engine with iterable types

## Lists
- Index 0: Item 1 
- Index 1: Item 2 
- Index 2: Item 3 

## Maps
- int: 5
- double: 6.7
- boolean: true
- text: This is a text
"""
    }

}
