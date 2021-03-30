<?php

namespace App\Form;

use App\Entity\Categorie;
use App\Entity\Employer;
use App\Entity\Formation;
use App\Entity\Tutor;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
class FormationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nomformation')
            ->add('tutor',EntityType::class, [
                'class' => Tutor::class, 'choice_label' => 'nom',
                'required'   => false,
            ])
            ->add('sujetdeformation',TextareaType::class)
            ->add('imageFile', FileType::class, [
                'mapped' => false
            ])


        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Formation::class,
        ]);
    }
}
