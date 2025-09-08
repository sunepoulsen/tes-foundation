#[[# Test of Velocity Engine with iterable types]]#

#[[## Lists]]#
#foreach ( $item in $list ) 
- Index $foreach.index: $item 
#end

#[[## Maps]]#
#foreach ( $key in $map.keySet() )
- $key: $map[$key]
#end
