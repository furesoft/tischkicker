package tischkicker.mappers;

import tischkicker.IModelMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectionMapper<TFrom, TTo> implements IModelMapper<TFrom, TTo> {

    @Override
    public TTo mapTo(TFrom obj) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return map(obj);
    }

    protected <From, To> To map(From obj) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class fromClass = obj.getClass();
        Class toClass = obj.getClass();

        To toObj = (To) toClass.getDeclaredConstructors()[0].newInstance();

        for (Field field : fromClass.getFields()) {
            toClass.getField(field.getName()).set(toObj, field.get(obj));
        }

        return toObj;
    }

    @Override
    public TFrom mapFrom(TTo obj) throws NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return map(obj);
    }
}
